import SharedLogic
import SwiftUI

struct PokemonTypeColors {
    let solid: Color
    let container: Color
    let onContainer: Color
}

extension PokemonType {
    var colors: PokemonTypeColors {
        switch self {
        case .grass:
            return PokemonTypeColors(solid: Color(hex: 0x74CB48), container: Color(hex: 0xBEE6AA), onContainer: Color(hex: 0x3A6B23))
        case .poison:
            return PokemonTypeColors(solid: Color(hex: 0xD270E0), container: Color(hex: 0xF2D5F6), onContainer: Color(hex: 0x90229F))
        case .fairy:
            return PokemonTypeColors(solid: Color(hex: 0xE37EB6), container: Color(hex: 0xF4CDE2), onContainer: Color(hex: 0x8A1E59))
        case .electric:
            return PokemonTypeColors(solid: Color(hex: 0xEFCE2A), container: Color(hex: 0xF8E89B), onContainer: Color(hex: 0x776408))
        case .bug:
            return PokemonTypeColors(solid: Color(hex: 0xB3CB48), container: Color(hex: 0xD4E6AA), onContainer: Color(hex: 0x4D6B23))
        case .dragon:
            return PokemonTypeColors(solid: Color(hex: 0xB170E0), container: Color(hex: 0xE3D5F6), onContainer: Color(hex: 0x67229F))
        case .water:
            return PokemonTypeColors(solid: Color(hex: 0x64BBDD), container: Color(hex: 0xC9E7F3), onContainer: Color(hex: 0x18556D))
        case .psychic:
            return PokemonTypeColors(solid: Color(hex: 0xE37E92), container: Color(hex: 0xF4CDD9), onContainer: Color(hex: 0x8A1E59))
        case .ground:
            return PokemonTypeColors(solid: Color(hex: 0xEFAA2A), container: Color(hex: 0xF8D79B), onContainer: Color(hex: 0x774708))
        case .fighting:
            return PokemonTypeColors(solid: Color(hex: 0xDB5C5C), container: Color(hex: 0xF3C9C9), onContainer: Color(hex: 0x681717))
        case .flying:
            return PokemonTypeColors(solid: Color(hex: 0x7E53D9), container: Color(hex: 0xCFC0F1), onContainer: Color(hex: 0x431F8F))
        case .ice:
            return PokemonTypeColors(solid: Color(hex: 0x64A1DD), container: Color(hex: 0xC9D6F3), onContainer: Color(hex: 0x182A6D))
        case .ghost:
            return PokemonTypeColors(solid: Color(hex: 0x5C53D9), container: Color(hex: 0xC0C2F1), onContainer: Color(hex: 0x231D81))
        case .normal:
            return PokemonTypeColors(solid: Color(hex: 0xB5B3AE), container: Color(hex: 0xDDDCDA), onContainer: Color(hex: 0x46443F))
        case .dark:
            return PokemonTypeColors(solid: Color(hex: 0x898989), container: Color(hex: 0xCDCDCD), onContainer: Color(hex: 0x4D4D4D))
        case .rock:
            return PokemonTypeColors(solid: Color(hex: 0x8897DB), container: Color(hex: 0xD8DDF3), onContainer: Color(hex: 0x2C3E91))
        case .fire:
            return PokemonTypeColors(solid: Color(hex: 0xDB7A5C), container: Color(hex: 0xF3D3C9), onContainer: Color(hex: 0x681717))
        case .steel:
            return PokemonTypeColors(solid: Color(hex: 0x88B1DB), container: Color(hex: 0xD8E8F3), onContainer: Color(hex: 0x2C5691))
        default:
            return PokemonTypeColors(solid: Color(hex: 0xA1A1A1), container: Color(hex: 0xE4E4E4), onContainer: Color(hex: 0x818181))
        }
    }
}
