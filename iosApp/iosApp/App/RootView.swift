import KMPObservableViewModelSwiftUI
import SharedLogic
import SwiftUI

/// App root: hosts the tabbed `HomeView` and applies the persisted theme mode to
/// the whole hierarchy, mirroring Android's `App` composable.
struct RootView: View {

    @StateViewModel private var settingsViewModel = SharedDI.settingsViewModel()

    var body: some View {
        HomeView()
            .tint(AppTheme.Colors.primary)
            .preferredColorScheme(preferredScheme)
    }

    /// `nil` follows the system appearance, matching `ThemeMode.SYSTEM`.
    private var preferredScheme: ColorScheme? {
        switch settingsViewModel.state.settings.themeMode {
        case .light: .light
        case .dark: .dark
        default: nil
        }
    }
}

#Preview {
    RootView()
}
