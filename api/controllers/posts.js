import { db } from "../connect.js";
import jwt from "jsonwebtoken";
import moment from "moment";

export const getPosts = (req, res) => {
    const userId = req.query.userId;
    const authorizationHeader = req.headers['authorization'];

    if (!authorizationHeader || !authorizationHeader.startsWith('Bearer ')) {
        return res.status(401).json("Not logged in!");
    }

    const token = authorizationHeader.split(' ')[1];

    jwt.verify(token, "secretkey", (err, userInfo) => {
        if (err) return res.status(403).json("Token is not valid!");

        console.log(userId);

        const q =
            userId !== "undefined"
                ? `SELECT p.*, u.id AS userId, name, profilePic FROM posts AS p JOIN users AS u ON (u.id = p.userId) WHERE p.userId = ? ORDER BY p.created_at DESC`
                : `SELECT p.*, u.id AS userId, name, profilePic FROM posts AS p JOIN users AS u ON (u.id = p.userId)
    LEFT JOIN relationships AS r ON (p.userId = r.followedUserId) WHERE r.followerUserId= ? OR p.userId =?
    ORDER BY p.created_at DESC`;

        const values =
            userId !== "undefined" ? [userId] : [userInfo.id, userInfo.id];

        db.query(q, values, (err, data) => {
            if (err) return res.status(500).json(err);
            return res.status(200).json(data);
        });
    });
};


export const addPost = (req, res) => {
    const authorizationHeader = req.headers['authorization'];
    if (!authorizationHeader || !authorizationHeader.startsWith('Bearer ')){
        return res.status(401).json("Not logged in !")
    }

    const token = authorizationHeader.split(' ')[1]; 

    jwt.verify(token, "secretkey", (err, userInfo) => {
        if (err) return res.status(403).json("Authentication not valid or Token is not valid!");


        /*
            Validation Post
         */

        const {caption, img} = req.body;
        if(!caption){
            return res.status(403).json("Caption is required!");
        }

        /*
            Query Database
         */
        const q =
            "INSERT INTO posts(`caption`, `img`, `created_at`, `userId`) VALUES (?)";
        const values = [
            caption,
            img || null,
            moment(Date.now()).format("YYYY-MM-DD HH:mm:ss"),
            userInfo.id,
        ];

        db.query(q, [values], (err, data) => {
            if (err) return res.status(500).json(err);
            return res.status(200).json("Post has been created.");
        });
    });
};

export const deletePost = (req, res) => {
    const authorizationHeader = req.headers['authorization'];
    if (!authorizationHeader || !authorizationHeader.startsWith('Bearer ')) {
        return res.status(401).json("Not logged in !")
    }

    const token = authorizationHeader.split(' ')[1]; 

    jwt.verify(token, "secretkey", (err, userInfo) => {
        if (err) return res.status(403).json("Token is not valid!");

        const q =
            "DELETE FROM posts WHERE `id`=? AND `userId` = ?";

        db.query(q, [req.params.id, userInfo.id], (err, data) => {
            if (err) return res.status(500).json(err);
            if (data.affectedRows > 0) return res.status(200).json("Post has been deleted.");
            return res.status(403).json("You can delete only your post")
        });
    });
};