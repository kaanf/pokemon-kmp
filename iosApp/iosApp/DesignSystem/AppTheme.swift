import SwiftUI

enum AppTheme {
    enum Spacing {
        static let xs: CGFloat = 4
        static let sm: CGFloat = 8
        static let md: CGFloat = 12
        static let lg: CGFloat = 16
        static let xl: CGFloat = 24
    }

    enum Radius {
        static let card: CGFloat = 16
    }

    enum Colors {
        static let background = Color.adaptive(light: Color(hex: 0xF1F1F1), dark: Color(hex: 0x202020))
        static let surface = Color.adaptive(light: Color(hex: 0xFAFAFA), dark: Color(hex: 0x181818))
        static let surfaceStroke = Color.adaptive(light: Color(hex: 0xE4E4E4), dark: Color(hex: 0x141414))

        static let surfaceTertiary = Color.adaptive(light: Color(hex: 0xE4E4E4), dark: Color(hex: 0x353535))

        static let contentPrimary = Color.adaptive(light: Color(hex: 0x3E3E3E), dark: Color(hex: 0xF0F0F0))
        static let contentSecondary = Color.adaptive(light: Color(hex: 0xA1A1A1), dark: Color(hex: 0x7A7777))
        static let contentTertiary = Color.adaptive(light: Color(hex: 0x818181), dark: Color(hex: 0x7A7777))
        static let onPrimary = Color.white

        static let onBackground = Color.adaptive(light: Color(hex: 0x818181), dark: Color(hex: 0xA2A2A2))

        static let dividerMedium = Color.adaptive(light: Color(hex: 0xE4E4E4), dark: Color(hex: 0x141414))

        static let primary = Color.adaptive(light: Color(hex: 0xF06449), dark: Color(hex: 0xBD3937))

        static let navigationPrimary = Color.adaptive(light: Color(argb: 0xB3FFFFFF), dark: Color(argb: 0x1FC8C8C8))

        static let pokeball = Color(hex: 0xF06449)

        static let buttonGradientStart = Color.adaptive(light: Color(hex: 0x6B6B6B), dark: Color(hex: 0xFFFFFF))
        static let buttonGradientEnd = Color.adaptive(light: Color(hex: 0x292929), dark: Color(hex: 0xCCCCCC))
        static let buttonText = Color.adaptive(light: Color(hex: 0xFFFFFF), dark: Color(hex: 0x3E3E3E))

        // Mirrors the Android shimmer palette: base is surfaceSecondary, the sweep
        // highlight is surfacePrimary in light and surfaceTertiary in dark.
        static let shimmerBase = Color.adaptive(light: Color(hex: 0xF1F1F1), dark: Color(hex: 0x202020))
        static let shimmerHighlight = Color.adaptive(light: Color(hex: 0xFAFAFA), dark: Color(hex: 0x353535))
    }
}
