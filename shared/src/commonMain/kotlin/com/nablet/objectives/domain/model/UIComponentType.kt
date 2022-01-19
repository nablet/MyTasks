package com.nablet.objectives.domain.model

sealed class UIComponentType {
	object Dialog : UIComponentType()
	object None : UIComponentType()
}
