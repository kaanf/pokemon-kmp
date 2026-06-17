import SwiftUI
import Kingfisher

struct PokemonArtwork: View {

    let url: String

    @State private var failed = false

    var body: some View {
        Group {
            if failed {
                Image(systemName: "questionmark.circle")
                    .foregroundStyle(AppTheme.Colors.contentSecondary)
            } else {
                KFImage(URL(string: url))
                    .placeholder { ProgressView() }
                    .onFailure { _ in failed = true }
                    .fade(duration: 0.2)
                    .resizable()
                    .scaledToFit()
            }
        }
        .onChange(of: url) { _, _ in failed = false }
    }
}
