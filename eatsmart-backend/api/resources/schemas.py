# schemas.py
from typing import List, Dict, Any
from pydantic import BaseModel, Field


class DishSchema(BaseModel):
    name: str
    ingredients: List[str]

    class Config:
        extra = "forbid"
        allow_population_by_field_name = True


class DishPortionSchema(BaseModel):
    dish: DishSchema
    portions: int

    class Config:
        extra = "forbid"
        allow_population_by_field_name = True


class NutrientsSchema(BaseModel):
    calories: int
    protein: int
    fat: int
    carbs: int

    class Config:
        extra = "forbid"
        allow_population_by_field_name = True


class DaySchema(BaseModel):
    date: str
    scheduleDescription: str
    dishes: List[DishPortionSchema]
    nutrients: NutrientsSchema

    class Config:
        extra = "forbid"
        allow_population_by_field_name = True

class IngredientSchema(BaseModel):
    name: str
    quantity: str

    class Config:
        extra = "forbid"
        allow_population_by_field_name = True

class MealPlanSchema(BaseModel):
    title: str
    startDate: str
    endDate: str
    days: List[DaySchema]
    ingredients: List[IngredientSchema]
    class Config:
        extra = "forbid"
        allow_population_by_field_name = True
