import KMPObservableViewModelSwiftUI
import SharedLogic
import SwiftUI

struct RootView: View {

    @StateViewModel private var settingsViewModel = SharedDI.settingsViewModel()

    var body: some View {
        HomeView()
            .tint(AppTheme.Colors.primary)
            .preferredColorScheme(settingsViewModel.state.settings.themeMode.colorScheme)
    }
}

#Preview {
    RootView()
}
