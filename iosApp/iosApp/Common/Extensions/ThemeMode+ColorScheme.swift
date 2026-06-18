import SharedLogic
import SwiftUI

extension ThemeMode {
    var colorScheme: ColorScheme? {
        switch self {
        case .light: .light
        case .dark: .dark
        default: nil
        }
    }
}
