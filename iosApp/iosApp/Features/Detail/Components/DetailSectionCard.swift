import SwiftUI

struct DetailSectionCard<Content: View>: View {

    @ViewBuilder let content: () -> Content

    var body: some View {
        content()
            .frame(maxWidth: .infinity)
            .background(AppTheme.Colors.surface)
            .clipShape(RoundedCornerShape(cornerRadius: 24))
            .overlay(
                RoundedCornerShape(cornerRadius: 24)
                    .stroke(AppTheme.Colors.surfaceStroke, lineWidth: 1)
            )
    }
}
