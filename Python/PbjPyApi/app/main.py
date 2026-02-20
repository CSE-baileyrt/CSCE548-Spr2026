# app/main.py
from typing import List
from fastapi import FastAPI, APIRouter, Depends, HTTPException, status
from sqlalchemy.ext.asyncio import AsyncSession

from .dependencies import get_db
from .repositories import (
    BreadRepo,
    PeanutButterRepo,
    JellyRepo,
    SandwichRepo,
)
from .schemas import (
    BreadCreate,
    BreadRead,
    BreadUpdate,
    PeanutButterCreate,
    PeanutButterRead,
    PeanutButterUpdate,
    JellyCreate,
    JellyRead,
    JellyUpdate,
    PbjSandwichCreate,
    PbjSandwichRead,
    PbjSandwichUpdate,
)
from .models import PbjSandwich

app = FastAPI(title="PBJ API")

# -------------------------
# Bread router
# -------------------------
bread_router = APIRouter(prefix="/breads", tags=["bread"])


@bread_router.post("/", response_model=BreadRead, status_code=status.HTTP_201_CREATED)
async def create_bread(payload: BreadCreate, db: AsyncSession = Depends(get_db)):
    repo = BreadRepo(db)
    bread = await repo.create(payload.brand, payload.wheatLevel, payload.price)
    return bread


@bread_router.get("/", response_model=List[BreadRead])
async def list_breads(db: AsyncSession = Depends(get_db)):
    repo = BreadRepo(db)
    return await repo.get_all()


@bread_router.get("/{bread_id}", response_model=BreadRead)
async def get_bread(bread_id: int, db: AsyncSession = Depends(get_db)):
    repo = BreadRepo(db)
    obj = await repo.get(bread_id)
    if not obj:
        raise HTTPException(status_code=404, detail="bread not found")
    return obj


@bread_router.put("/{bread_id}", response_model=BreadRead)
async def replace_bread(bread_id: int, payload: BreadCreate, db: AsyncSession = Depends(get_db)):
    repo = BreadRepo(db)
    obj = await repo.get(bread_id)
    if not obj:
        raise HTTPException(status_code=404, detail="bread not found")
    # replace fields explicitly
    updated = await repo.update(bread_id, brand=payload.brand, wheatLevel=payload.wheatLevel, price=payload.price)
    return updated


@bread_router.patch("/{bread_id}", response_model=BreadRead)
async def update_bread(bread_id: int, payload: BreadUpdate, db: AsyncSession = Depends(get_db)):
    repo = BreadRepo(db)
    obj = await repo.get(bread_id)
    if not obj:
        raise HTTPException(status_code=404, detail="bread not found")
    updated = await repo.update(bread_id, **payload.dict(exclude_unset=True))
    return updated


@bread_router.delete("/{bread_id}", status_code=status.HTTP_204_NO_CONTENT)
async def delete_bread(bread_id: int, db: AsyncSession = Depends(get_db)):
    repo = BreadRepo(db)
    ok = await repo.delete(bread_id)
    if not ok:
        raise HTTPException(status_code=404, detail="bread not found")
    return None


# -------------------------
# PeanutButter router
# -------------------------
pb_router = APIRouter(prefix="/peanutbutters", tags=["peanutbutters"])


@pb_router.post("/", response_model=PeanutButterRead, status_code=status.HTTP_201_CREATED)
async def create_pb(payload: PeanutButterCreate, db: AsyncSession = Depends(get_db)):
    repo = PeanutButterRepo(db)
    obj = await repo.create(payload.brand, payload.isCrunchy, payload.price)
    return obj


@pb_router.get("/", response_model=List[PeanutButterRead])
async def list_pbs(db: AsyncSession = Depends(get_db)):
    repo = PeanutButterRepo(db)
    return await repo.get_all()


@pb_router.get("/{pb_id}", response_model=PeanutButterRead)
async def get_pb(pb_id: int, db: AsyncSession = Depends(get_db)):
    repo = PeanutButterRepo(db)
    obj = await repo.get(pb_id)
    if not obj:
        raise HTTPException(status_code=404, detail="peanut butter not found")
    return obj


@pb_router.put("/{pb_id}", response_model=PeanutButterRead)
async def replace_pb(pb_id: int, payload: PeanutButterCreate, db: AsyncSession = Depends(get_db)):
    repo = PeanutButterRepo(db)
    obj = await repo.get(pb_id)
    if not obj:
        raise HTTPException(status_code=404, detail="peanut butter not found")
    updated = await repo.update(pb_id, brand=payload.brand, isCrunchy=payload.isCrunchy, price=payload.price)
    return updated


@pb_router.patch("/{pb_id}", response_model=PeanutButterRead)
async def update_pb(pb_id: int, payload: PeanutButterUpdate, db: AsyncSession = Depends(get_db)):
    repo = PeanutButterRepo(db)
    obj = await repo.get(pb_id)
    if not obj:
        raise HTTPException(status_code=404, detail="peanut butter not found")
    updated = await repo.update(pb_id, **payload.dict(exclude_unset=True))
    return updated


@pb_router.delete("/{pb_id}", status_code=status.HTTP_204_NO_CONTENT)
async def delete_pb(pb_id: int, db: AsyncSession = Depends(get_db)):
    repo = PeanutButterRepo(db)
    ok = await repo.delete(pb_id)
    if not ok:
        raise HTTPException(status_code=404, detail="peanut butter not found")
    return None


# -------------------------
# Jelly router
# -------------------------
jelly_router = APIRouter(prefix="/jellies", tags=["jellies"])


@jelly_router.post("/", response_model=JellyRead, status_code=status.HTTP_201_CREATED)
async def create_jelly(payload: JellyCreate, db: AsyncSession = Depends(get_db)):
    repo = JellyRepo(db)
    obj = await repo.create(payload.brand, payload.flavor, payload.price)
    return obj


@jelly_router.get("/", response_model=List[JellyRead])
async def list_jellies(db: AsyncSession = Depends(get_db)):
    repo = JellyRepo(db)
    return await repo.get_all()


@jelly_router.get("/{jelly_id}", response_model=JellyRead)
async def get_jelly(jelly_id: int, db: AsyncSession = Depends(get_db)):
    repo = JellyRepo(db)
    obj = await repo.get(jelly_id)
    if not obj:
        raise HTTPException(status_code=404, detail="jelly not found")
    return obj


@jelly_router.put("/{jelly_id}", response_model=JellyRead)
async def replace_jelly(jelly_id: int, payload: JellyCreate, db: AsyncSession = Depends(get_db)):
    repo = JellyRepo(db)
    obj = await repo.get(jelly_id)
    if not obj:
        raise HTTPException(status_code=404, detail="jelly not found")
    updated = await repo.update(jelly_id, brand=payload.brand, flavor=payload.flavor, price=payload.price)
    return updated


@jelly_router.patch("/{jelly_id}", response_model=JellyRead)
async def update_jelly(jelly_id: int, payload: JellyUpdate, db: AsyncSession = Depends(get_db)):
    repo = JellyRepo(db)
    obj = await repo.get(jelly_id)
    if not obj:
        raise HTTPException(status_code=404, detail="jelly not found")
    updated = await repo.update(jelly_id, **payload.dict(exclude_unset=True))
    return updated


@jelly_router.delete("/{jelly_id}", status_code=status.HTTP_204_NO_CONTENT)
async def delete_jelly(jelly_id: int, db: AsyncSession = Depends(get_db)):
    repo = JellyRepo(db)
    ok = await repo.delete(jelly_id)
    if not ok:
        raise HTTPException(status_code=404, detail="jelly not found")
    return None


# -------------------------
# Sandwich router
# -------------------------
sandwich_router = APIRouter(prefix="/sandwiches", tags=["sandwiches"])


@sandwich_router.post("/", response_model=PbjSandwichRead, status_code=status.HTTP_201_CREATED)
async def create_sandwich(payload: PbjSandwichCreate, db: AsyncSession = Depends(get_db)):
    bread_repo = BreadRepo(db)
    pb_repo = PeanutButterRepo(db)
    jelly_repo = JellyRepo(db)
    sandwich_repo = SandwichRepo(db)

    # fetch components
    b1 = await bread_repo.get(payload.bread1_id)
    if not b1:
        raise HTTPException(status_code=404, detail="bread1 not found")
    pb = await pb_repo.get(payload.pb_id)
    if not pb:
        raise HTTPException(status_code=404, detail="peanut butter not found")
    jelly = await jelly_repo.get(payload.jelly_id)
    if not jelly:
        raise HTTPException(status_code=404, detail="jelly not found")
    b2 = await bread_repo.get(payload.bread2_id)
    if not b2:
        raise HTTPException(status_code=404, detail="bread2 not found")

    # compute totalCost if caller didn't provide it
    if payload.totalCost is None:
        sandwich = await sandwich_repo.create_from_components(
            customer=payload.customer, bread1=b1, pb=pb, jelly=jelly, bread2=b2
        )
    else:
        sandwich = await sandwich_repo.create(
            customer=payload.customer,
            bread1_id=b1.id,
            pb_id=pb.id,
            jelly_id=jelly.id,
            bread2_id=b2.id,
            totalCost=payload.totalCost,
        )

    # ensure relationships are loaded before session closes
    # refresh with relationship names to load them
    await db.refresh(sandwich, attribute_names=["bread1", "bread2", "peanut_butter", "jelly"])
    return sandwich


@sandwich_router.get("/", response_model=List[PbjSandwichRead])
async def list_sandwiches(db: AsyncSession = Depends(get_db)):
    sandwich_repo = SandwichRepo(db)
    sandwiches = await sandwich_repo.get_all()
    # eager-load relationships for each sandwich before session closes
    for s in sandwiches:
        await db.refresh(s, attribute_names=["bread1", "bread2", "peanut_butter", "jelly"])
    return sandwiches


@sandwich_router.get("/{sandwich_id}", response_model=PbjSandwichRead)
async def get_sandwich(sandwich_id: int, db: AsyncSession = Depends(get_db)):
    sandwich_repo = SandwichRepo(db)
    s = await sandwich_repo.get(sandwich_id)
    if not s:
        raise HTTPException(status_code=404, detail="sandwich not found")
    await db.refresh(s, attribute_names=["bread1", "bread2", "peanut_butter", "jelly"])
    return s


@sandwich_router.put("/{sandwich_id}", response_model=PbjSandwichRead)
async def replace_sandwich(sandwich_id: int, payload: PbjSandwichCreate, db: AsyncSession = Depends(get_db)):
    bread_repo = BreadRepo(db)
    pb_repo = PeanutButterRepo(db)
    jelly_repo = JellyRepo(db)
    sandwich_repo = SandwichRepo(db)

    # verify components exist
    b1 = await bread_repo.get(payload.bread1_id)
    if not b1:
        raise HTTPException(status_code=404, detail="bread1 not found")
    pb = await pb_repo.get(payload.pb_id)
    if not pb:
        raise HTTPException(status_code=404, detail="peanut butter not found")
    jelly = await jelly_repo.get(payload.jelly_id)
    if not jelly:
        raise HTTPException(status_code=404, detail="jelly not found")
    b2 = await bread_repo.get(payload.bread2_id)
    if not b2:
        raise HTTPException(status_code=404, detail="bread2 not found")

    total = payload.totalCost if payload.totalCost is not None else float(b1.price) + float(pb.price) + float(jelly.price) + float(b2.price)
    updated = await sandwich_repo.update(
        sandwich_id,
        customer=payload.customer,
        bread1_id=b1.id,
        pb_id=pb.id,
        jelly_id=jelly.id,
        bread2_id=b2.id,
        totalCost=total,
    )
    if not updated:
        raise HTTPException(status_code=404, detail="sandwich not found")
    await db.refresh(updated, attribute_names=["bread1", "bread2", "peanut_butter", "jelly"])
    return updated


@sandwich_router.patch("/{sandwich_id}", response_model=PbjSandwichRead)
async def update_sandwich(sandwich_id: int, payload: PbjSandwichUpdate, db: AsyncSession = Depends(get_db)):
    sandwich_repo = SandwichRepo(db)
    bread_repo = BreadRepo(db)
    pb_repo = PeanutButterRepo(db)
    jelly_repo = JellyRepo(db)

    current = await sandwich_repo.get(sandwich_id)
    if not current:
        raise HTTPException(status_code=404, detail="sandwich not found")

    update_data = payload.dict(exclude_unset=True)

    # If IDs of components are provided, validate existence and optionally recompute totalCost.
    if "bread1_id" in update_data:
        b1 = await bread_repo.get(update_data["bread1_id"])
        if not b1:
            raise HTTPException(status_code=404, detail="bread1 not found")
    else:
        b1 = await bread_repo.get(current.bread1_id)

    if "pb_id" in update_data:
        pb = await pb_repo.get(update_data["pb_id"])
        if not pb:
            raise HTTPException(status_code=404, detail="peanut butter not found")
    else:
        pb = await pb_repo.get(current.pb_id)

    if "jelly_id" in update_data:
        jelly = await jelly_repo.get(update_data["jelly_id"])
        if not jelly:
            raise HTTPException(status_code=404, detail="jelly not found")
    else:
        jelly = await jelly_repo.get(current.jelly_id)

    if "bread2_id" in update_data:
        b2 = await bread_repo.get(update_data["bread2_id"])
        if not b2:
            raise HTTPException(status_code=404, detail="bread2 not found")
    else:
        b2 = await bread_repo.get(current.bread2_id)

    # recompute totalCost if any component changed or if caller didn't provide totalCost in update
    if any(k in update_data for k in ("bread1_id", "pb_id", "jelly_id", "bread2_id")) and "totalCost" not in update_data:
        update_data["totalCost"] = float(b1.price) + float(pb.price) + float(jelly.price) + float(b2.price)

    updated = await sandwich_repo.update(sandwich_id, **update_data)
    await db.refresh(updated, attribute_names=["bread1", "bread2", "peanut_butter", "jelly"])
    return updated


@sandwich_router.delete("/{sandwich_id}", status_code=status.HTTP_204_NO_CONTENT)
async def delete_sandwich(sandwich_id: int, db: AsyncSession = Depends(get_db)):
    sandwich_repo = SandwichRepo(db)
    ok = await sandwich_repo.delete(sandwich_id)
    if not ok:
        raise HTTPException(status_code=404, detail="sandwich not found")
    return None


# -------------------------
# Include routers
# -------------------------
app.include_router(bread_router)
app.include_router(pb_router)
app.include_router(jelly_router)
app.include_router(sandwich_router)