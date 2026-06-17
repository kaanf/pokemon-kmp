package com.kaanf.pokemon.presentation.detail

import com.kaanf.pokemon.core.common.DataError
import com.kaanf.pokemon.presentation.detail.model.DetailUiModel

data class DetailState(
    val detail: DetailUiModel? = null,
    val isLoading: Boolean = false,
    val error: DataError.Remote? = null,
)
