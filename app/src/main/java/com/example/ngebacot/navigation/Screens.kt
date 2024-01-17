package com.example.ngebacot.navigation

import androidx.annotation.StringRes
import com.example.ngebacot.R

enum class Screens {
    HomePage,
    ProfilePage
}

//sealed class Screens(val route: String, @StringRes val resourceId: Int) {
//    object ProfilePage: Screens("profilepage", R.string.profile_page)
//}