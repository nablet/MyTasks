package com.nablet.mytasks.domain.model

sealed class UIComponentType {
	object Dialog : UIComponentType()
	object SnackBar : UIComponentType()
	object None : UIComponentType()
}
