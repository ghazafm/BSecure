import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.mawar.bsecure.model.AppUser

class ProfileViewModel : ViewModel() {
    // Mutable state for holding the user profile
    var userProfile = mutableStateOf<AppUser?>(null)
        private set

    // Method to update the user profile data
    fun updateUserProfile(name: String, email: String, profilePictureUrl: String) {
        userProfile.value = AppUser(name, email, profilePictureUrl)
    }

    // Method to set the initial profile data after login
    fun setInitialUserProfile(user: AppUser) {
        userProfile.value = user
    }
}
