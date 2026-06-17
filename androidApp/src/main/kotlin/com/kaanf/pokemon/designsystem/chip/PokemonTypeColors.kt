package com.kaanf.pokemon.designsystem.chip

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import com.kaanf.pokemon.designsystem.DividerMedium
import com.kaanf.pokemon.designsystem.OnTypeBugContainer
import com.kaanf.pokemon.designsystem.OnTypeDarkContainer
import com.kaanf.pokemon.designsystem.OnTypeDragonContainer
import com.kaanf.pokemon.designsystem.OnTypeElectricContainer
import com.kaanf.pokemon.designsystem.OnTypeFairyContainer
import com.kaanf.pokemon.designsystem.OnTypeFightingContainer
import com.kaanf.pokemon.designsystem.OnTypeFireContainer
import com.kaanf.pokemon.designsystem.OnTypeFlyingContainer
import com.kaanf.pokemon.designsystem.OnTypeGhostContainer
import com.kaanf.pokemon.designsystem.OnTypeGrassContainer
import com.kaanf.pokemon.designsystem.OnTypeGroundContainer
import com.kaanf.pokemon.designsystem.OnTypeIceContainer
import com.kaanf.pokemon.designsystem.OnTypeNormalContainer
import com.kaanf.pokemon.designsystem.OnTypePoisonContainer
import com.kaanf.pokemon.designsystem.OnTypePsychicContainer
import com.kaanf.pokemon.designsystem.OnTypeRockContainer
import com.kaanf.pokemon.designsystem.OnTypeSteelContainer
import com.kaanf.pokemon.designsystem.OnTypeWaterContainer
import com.kaanf.pokemon.designsystem.SurfaceSecondary
import com.kaanf.pokemon.designsystem.SurfaceTertiary
import com.kaanf.pokemon.designsystem.TypeBug
import com.kaanf.pokemon.designsystem.TypeBugContainer
import com.kaanf.pokemon.designsystem.TypeDark
import com.kaanf.pokemon.designsystem.TypeDarkContainer
import com.kaanf.pokemon.designsystem.TypeDragon
import com.kaanf.pokemon.designsystem.TypeDragonContainer
import com.kaanf.pokemon.designsystem.TypeElectric
import com.kaanf.pokemon.designsystem.TypeElectricContainer
import com.kaanf.pokemon.designsystem.TypeFairy
import com.kaanf.pokemon.designsystem.TypeFairyContainer
import com.kaanf.pokemon.designsystem.TypeFighting
import com.kaanf.pokemon.designsystem.TypeFightingContainer
import com.kaanf.pokemon.designsystem.TypeFire
import com.kaanf.pokemon.designsystem.TypeFireContainer
import com.kaanf.pokemon.designsystem.TypeFlying
import com.kaanf.pokemon.designsystem.TypeFlyingContainer
import com.kaanf.pokemon.designsystem.TypeGhost
import com.kaanf.pokemon.designsystem.TypeGhostContainer
import com.kaanf.pokemon.designsystem.TypeGrass
import com.kaanf.pokemon.designsystem.TypeGrassContainer
import com.kaanf.pokemon.designsystem.TypeGround
import com.kaanf.pokemon.designsystem.TypeGroundContainer
import com.kaanf.pokemon.designsystem.TypeIce
import com.kaanf.pokemon.designsystem.TypeIceContainer
import com.kaanf.pokemon.designsystem.TypeNormal
import com.kaanf.pokemon.designsystem.TypeNormalContainer
import com.kaanf.pokemon.designsystem.TypePoison
import com.kaanf.pokemon.designsystem.TypePoisonContainer
import com.kaanf.pokemon.designsystem.TypePsychic
import com.kaanf.pokemon.designsystem.TypePsychicContainer
import com.kaanf.pokemon.designsystem.TypeRock
import com.kaanf.pokemon.designsystem.TypeRockContainer
import com.kaanf.pokemon.designsystem.TypeSteel
import com.kaanf.pokemon.designsystem.TypeSteelContainer
import com.kaanf.pokemon.designsystem.TypeWater
import com.kaanf.pokemon.designsystem.TypeWaterContainer
import com.kaanf.pokemon.domain.model.PokemonType

@Immutable
data class PokemonTypeColors(
    val solid: Color,
    val container: Color,
    val onContainer: Color,
)

fun getPokemonTypeColors(type: PokemonType): PokemonTypeColors = when (type) {
    PokemonType.GRASS -> PokemonTypeColors(TypeGrass, TypeGrassContainer, OnTypeGrassContainer)
    PokemonType.POISON -> PokemonTypeColors(TypePoison, TypePoisonContainer, OnTypePoisonContainer)
    PokemonType.FAIRY -> PokemonTypeColors(TypeFairy, TypeFairyContainer, OnTypeFairyContainer)
    PokemonType.ELECTRIC -> PokemonTypeColors(
        TypeElectric,
        TypeElectricContainer,
        OnTypeElectricContainer
    )
    PokemonType.BUG -> PokemonTypeColors(TypeBug, TypeBugContainer, OnTypeBugContainer)
    PokemonType.DRAGON -> PokemonTypeColors(TypeDragon, TypeDragonContainer, OnTypeDragonContainer)
    PokemonType.WATER -> PokemonTypeColors(TypeWater, TypeWaterContainer, OnTypeWaterContainer)
    PokemonType.PSYCHIC -> PokemonTypeColors(
        TypePsychic,
        TypePsychicContainer,
        OnTypePsychicContainer
    )
    PokemonType.GROUND -> PokemonTypeColors(TypeGround, TypeGroundContainer, OnTypeGroundContainer)
    PokemonType.FIGHTING -> PokemonTypeColors(
        TypeFighting,
        TypeFightingContainer,
        OnTypeFightingContainer
    )
    PokemonType.FLYING -> PokemonTypeColors(TypeFlying, TypeFlyingContainer, OnTypeFlyingContainer)
    PokemonType.ICE -> PokemonTypeColors(TypeIce, TypeIceContainer, OnTypeIceContainer)
    PokemonType.GHOST -> PokemonTypeColors(TypeGhost, TypeGhostContainer, OnTypeGhostContainer)
    PokemonType.NORMAL -> PokemonTypeColors(TypeNormal, TypeNormalContainer, OnTypeNormalContainer)
    PokemonType.DARK -> PokemonTypeColors(TypeDark, TypeDarkContainer, OnTypeDarkContainer)
    PokemonType.ROCK -> PokemonTypeColors(TypeRock, TypeRockContainer, OnTypeRockContainer)
    PokemonType.FIRE -> PokemonTypeColors(TypeFire, TypeFireContainer, OnTypeFireContainer)
    PokemonType.STEEL -> PokemonTypeColors(TypeSteel, TypeSteelContainer, OnTypeSteelContainer)
    PokemonType.UNKNOWN -> PokemonTypeColors(
        solid = SurfaceSecondary,
        container = DividerMedium,
        onContainer = SurfaceTertiary,
    )
}
