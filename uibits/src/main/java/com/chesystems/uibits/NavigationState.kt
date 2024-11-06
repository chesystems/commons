import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

@Composable
fun <T : Enum<T>> rememberNavigationState(
    fromRoute: (String?) -> T
): NavigationState<T> {
    val navController = rememberNavController()
    val currentRoute by navController.currentBackStackEntryAsState()
    val screen = fromRoute(currentRoute?.destination?.route)
    
    return remember(navController, screen) {
        NavigationState(
            navHost = navController,
            screen = screen
        )
    }
}

data class NavigationState<T>(
    val navHost: NavHostController,
    val screen: T
) 

/* USE ........................
val navState = rememberNavigationState(
    fromRoute = MyNavigation::fromRoute
)

REQUIRES .......................
enum class MyNavigation {
    SCREEN,
    ;

    companion object {
        /** Convert route to destination, defaulting to SCREEN */
        fun fromRoute(route: String?): MyNavigation =
            when (route?.substringBefore("/")) {
                SCREEN.name -> SCREEN
                null -> SCREEN
                else -> throw IllegalArgumentException("Route $route is not recognized.")
            }
    }
}
*/

/* MADE TO AVOID THE WHOLE THING
val navHost = rememberNavController().apply { enableOnBackPressed(false) }
    val backstackEntry by navHost.currentBackStackEntryAsState()
    val screen = MyNavigation.fromRoute(backstackEntry?.destination?.route)
 */