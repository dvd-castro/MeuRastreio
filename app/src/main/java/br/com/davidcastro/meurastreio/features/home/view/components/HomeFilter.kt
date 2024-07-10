package br.com.davidcastro.meurastreio.features.home.view.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.AbsoluteRoundedCornerShape
import androidx.compose.material3.FilterChip
import androidx.compose.material3.InputChipDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import br.com.davidcastro.meurastreio.commons.utils.Dimens
import br.com.davidcastro.meurastreio.core.theme.GetPrimaryColor
import br.com.davidcastro.meurastreio.domain.model.StateEnum

@Composable
fun HomeFilter(
    items: List<String>,
    onSelected: (String) -> Unit
) {
    var selectedItem by rememberSaveable {
        mutableStateOf(items.first())
    }

    LazyRow(
        modifier = Modifier.padding(vertical = Dimens.dimen8dp).fillMaxWidth()
    ) {
        items(items) {
            FilterChip(
                selected = selectedItem == it,
                border = null,
                colors = if(selectedItem == it)
                    InputChipDefaults.inputChipColors(containerColor = GetPrimaryColor())
                else
                    InputChipDefaults.inputChipColors(containerColor = GetPrimaryColor()),
                label = {
                    Text(
                        text = it,
                        fontSize = Dimens.size14sp
                    )
                },
                onClick = {
                    selectedItem = it
                    onSelected(it)
                },
                modifier = Modifier.padding(end = Dimens.dimen8dp),
                shape = AbsoluteRoundedCornerShape(Dimens.dimen16dp)
            )
        }
    }
}

@Preview
@Composable
fun HomeFilterPreview() {
    HomeFilter(
        items = StateEnum.entries.map { it.value },
        onSelected = {}
    )
}