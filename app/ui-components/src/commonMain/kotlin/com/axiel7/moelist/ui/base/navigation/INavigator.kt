package com.axiel7.moelist.ui.base.navigation

interface INavigator {
    fun navigate(route: Route)
    fun goBack()
}