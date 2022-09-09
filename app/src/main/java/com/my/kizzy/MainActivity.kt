package com.my.kizzy

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.my.kizzy.ui.common.Routes
import com.my.kizzy.ui.common.animatedComposable
import com.my.kizzy.ui.screen.apps.AppsRPC
import com.my.kizzy.ui.screen.custom.CustomRPC
import com.my.kizzy.ui.screen.home.Home
import com.my.kizzy.ui.screen.media.MediaRPC
import com.my.kizzy.ui.screen.profile.Login
import com.my.kizzy.ui.screen.profile.Profile
import com.my.kizzy.ui.screen.settings.RpcSettings
import com.my.kizzy.ui.screen.settings.Settings
import com.my.kizzy.ui.screen.settings.about.About
import com.my.kizzy.ui.screen.settings.language.Language
import com.my.kizzy.ui.screen.settings.style.Appearance
import com.my.kizzy.ui.theme.AppTypography
import com.my.kizzy.utils.Prefs
import me.rerere.md3compat.Md3CompatTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Md3CompatTheme(typography = AppTypography) {
                Kizzy()
            }
        }
    }

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @OptIn(ExperimentalAnimationApi::class, ExperimentalMaterial3Api::class)
    @Composable
    fun Kizzy() {
        Scaffold()
        {
            val navcontroller = rememberAnimatedNavController()
            AnimatedNavHost(
                navController = navcontroller,
                startDestination = Routes.HOME
            ) {
                animatedComposable(Routes.HOME) {
                    Home(navController = navcontroller)
                }
                animatedComposable(Routes.SETTINGS) {
                    Settings(
                        onBackPressed = {
                            navcontroller.popBackStack()
                        },
                        navigateToAbout = {
                            navcontroller.navigate(Routes.ABOUT) {
                                launchSingleTop = true
                            }
                        },
                        navigateToProfile = {
                            navcontroller.navigate(Routes.PROFILE) {
                                launchSingleTop = true
                            }
                        },
                        navigateToStyleAndAppeareance = {
                            navcontroller.navigate(Routes.STYLE_AND_APPEAREANCE) {
                                launchSingleTop = true
                            }
                        }
                    )
                }
                animatedComposable(Routes.APPS_DETECTION) { AppsRPC(onBackPressed = {navcontroller.popBackStack()} ) }
                animatedComposable(Routes.CUSTOM_RPC) { CustomRPC(onBackPressed = {navcontroller.popBackStack()} ) }
                animatedComposable(Routes.MEDIA_RPC) { MediaRPC(onBackPressed = {navcontroller.popBackStack()} ) }
                animatedComposable(Routes.PROFILE) {
                    if (Prefs[Prefs.TOKEN, ""].isEmpty()) {
                        navcontroller.navigate(Routes.LOGIN)
                    } else {
                        Profile(onBackPressed = {
                            navcontroller.popBackStack()
                        })
                    }
                }
                animatedComposable(Routes.LOGIN) { Login(onBackPressed = {
                    navcontroller.popBackStack()
                }) }
                animatedComposable(Routes.RPC_SETTINGS) { RpcSettings(onBackPressed = {
                    navcontroller.popBackStack()
                }) }
                animatedComposable(Routes.LANGUAGES) { Language(onBackPressed = {
                    navcontroller.popBackStack()
                }) }
                animatedComposable(Routes.STYLE_AND_APPEAREANCE) { Appearance(onBackPressed = {
                    navcontroller.popBackStack()
                }, navigateToLanguages = {
                    navcontroller.navigate(Routes.LANGUAGES)
                }) }
                animatedComposable(Routes.ABOUT) { About() }
            }
        }
    }


    @Preview(showBackground = true)
    @Composable
    fun DefaultPreview() {
        MaterialTheme {
        }
    }
}