import SwiftUI

enum AppTab: CaseIterable, Hashable {
    case pokedex
    case team

    var titleKey: LocalizedStringKey {
        switch self {
        case .pokedex: "pokedex.title"
        case .team: "team.title"
        }
    }
}
