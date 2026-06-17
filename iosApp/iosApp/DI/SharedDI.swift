import SharedLogic

enum SharedDI {
    static func start() {
        InitKoinKt.doInitKoin(config: nil)
    }

    static func pokedexViewModel() -> PokedexViewModel {
        IosViewModelsKt.pokedexViewModel()
    }

    static func teamViewModel() -> TeamViewModel {
        IosViewModelsKt.teamViewModel()
    }

    static func homeViewModel() -> HomeViewModel {
        IosViewModelsKt.homeViewModel()
    }

    static func settingsViewModel() -> SettingsViewModel {
        IosViewModelsKt.settingsViewModel()
    }

    static func detailViewModel(pokemonId: Int) -> DetailViewModel {
        IosViewModelsKt.detailViewModel(pokemonId: Int32(pokemonId))
    }
}
