import SwiftUI

@main
struct PokemonApp: App {

    init() {
        Self.setUpDependencies()
    }

    var body: some Scene {
        WindowGroup {
            RootView()
        }
    }
}

private extension PokemonApp {

    static func setUpDependencies() {
        SharedDI.start()
    }
}
