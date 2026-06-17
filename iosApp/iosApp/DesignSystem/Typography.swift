import SwiftUI

struct AppFont {
    let font: Font
    let tracking: CGFloat
}

extension View {
    func appFont(_ style: AppFont) -> some View {
        font(style.font).tracking(style.tracking)
    }
}

extension AppTheme {
    enum Typography {
        static let headlineLarge = AppFont(
            font: .custom("Inter-Bold", size: 30, relativeTo: .title),
            tracking: -1.41 // -0.047em * 30pt
        )
        static let headlineMedium = AppFont(
            font: .custom("Inter-Bold", size: 20, relativeTo: .title3),
            tracking: 0
        )
        static let headlineSmall = AppFont(
            font: .custom("Inter-Bold", size: 16, relativeTo: .body),
            tracking: 0
        )
        static let titleLarge = AppFont(
            font: .custom("Inter-SemiBold", size: 16, relativeTo: .headline),
            tracking: 0
        )
        static let titleMedium = AppFont(
            font: .custom("Inter-Medium", size: 16, relativeTo: .body),
            tracking: 0
        )
        static let titleSmall = AppFont(
            font: .custom("Inter-ExtraBold", size: 16, relativeTo: .headline),
            tracking: 0
        )
        static let bodyMedium = AppFont(
            font: .custom("Inter-Regular", size: 12, relativeTo: .caption),
            tracking: 0
        )

        static let monoLarge = AppFont(
            font: .custom("ShareTechMono-Regular", size: 20, relativeTo: .title3),
            tracking: 0
        )
        static let monoMedium = AppFont(
            font: .custom("ShareTechMono-Regular", size: 16, relativeTo: .body),
            tracking: 0
        )
        static let monoSmall = AppFont(
            font: .custom("ShareTechMono-Regular", size: 13, relativeTo: .footnote),
            tracking: 0
        )
    }
}
