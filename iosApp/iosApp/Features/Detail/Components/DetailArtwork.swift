import SharedLogic
import SwiftUI

struct DetailArtwork: View {
    let imageURL: String
    let shinyImageURL: String
    let backgroundColor: Color
    let topPadding: CGFloat
    var fillsHeight: Bool = false

    @State private var isShiny = false

    private static let shinyColor = Color(hex: 0xFFB300)

    var body: some View {
        PokemonArtwork(url: isShiny ? shinyImageURL : imageURL)
            .frame(width: 230, height: 230)
            .padding(AppTheme.Spacing.lg)
            .padding(.top, topPadding)
            .frame(maxWidth: .infinity, maxHeight: fillsHeight ? .infinity : nil)
            .id(isShiny)
            .transition(.opacity)
            .overlay(alignment: .bottomLeading) {
                shinyToggle
                    .padding(AppTheme.Spacing.lg)
            }
            .frame(maxWidth: .infinity)
            .background(backgroundColor)
            .animation(.easeInOut(duration: 0.3), value: isShiny)
    }

    private var shinyToggle: some View {
        Toggle("", isOn: $isShiny)
            .labelsHidden()
            .tint(Self.shinyColor)
            .accessibilityLabel(Text("detail.toggle_shiny"))
    }
}
