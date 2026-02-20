# app/schemas.py
from typing import Optional, List
from pydantic import BaseModel


# ---------------------------
# Bread DTOs
# ---------------------------
class BreadBase(BaseModel):
    brand: Optional[str] = None
    wheatLevel: Optional[int] = None
    price: Optional[float] = None


class BreadCreate(BreadBase):
    brand: str
    wheatLevel: int
    price: float


class BreadUpdate(BreadBase):
    # all fields optional for partial update
    pass


class BreadRead(BreadBase):
    id: int
    brand: str
    wheatLevel: int
    price: float

    class Config:
        orm_mode = True


# ---------------------------
# PeanutButter DTOs
# ---------------------------
class PeanutButterBase(BaseModel):
    brand: Optional[str] = None
    isCrunchy: Optional[bool] = None
    price: Optional[float] = None


class PeanutButterCreate(PeanutButterBase):
    brand: str
    isCrunchy: bool
    price: float


class PeanutButterUpdate(PeanutButterBase):
    pass


class PeanutButterRead(PeanutButterBase):
    id: int
    brand: str
    isCrunchy: bool
    price: float

    class Config:
        orm_mode = True


# ---------------------------
# Jelly DTOs
# ---------------------------
class JellyBase(BaseModel):
    brand: Optional[str] = None
    flavor: Optional[str] = None
    price: Optional[float] = None


class JellyCreate(JellyBase):
    brand: str
    flavor: str
    price: float


class JellyUpdate(JellyBase):
    pass


class JellyRead(JellyBase):
    id: int
    brand: str
    flavor: str
    price: float

    class Config:
        orm_mode = True


# ---------------------------
# PBJ Sandwich DTOs
# ---------------------------
class PbjSandwichBase(BaseModel):
    customer: Optional[str] = None
    bread1_id: Optional[int] = None
    pb_id: Optional[int] = None
    jelly_id: Optional[int] = None
    bread2_id: Optional[int] = None
    totalCost: Optional[float] = None


class PbjSandwichCreate(BaseModel):
    customer: str
    bread1_id: int
    pb_id: int
    jelly_id: int
    bread2_id: int
    # totalCost may be omitted; service can compute it. If provided, repo will accept it.
    totalCost: Optional[float] = None


class PbjSandwichUpdate(PbjSandwichBase):
    # partial updates allowed
    pass


class PbjSandwichRead(BaseModel):
    id: int
    customer: str
    bread1: BreadRead
    peanut_butter: PeanutButterRead
    jelly: JellyRead
    bread2: BreadRead
    totalCost: float

    class Config:
        orm_mode = True


# ---------------------------
# List responses (optional helpers)
# ---------------------------
class BreadList(BaseModel):
    items: List[BreadRead]


class PeanutButterList(BaseModel):
    items: List[PeanutButterRead]


class JellyList(BaseModel):
    items: List[JellyRead]


class PbjSandwichList(BaseModel):
    items: List[PbjSandwichRead]