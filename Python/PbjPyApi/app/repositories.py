# app/repositories.py
from typing import List, Optional, Dict, Any
from sqlalchemy.ext.asyncio import AsyncSession
from sqlalchemy import select, update as sql_update
from .models import Bread, PeanutButter, Jelly, PbjSandwich


# ---------------------------
# Generic helper (optional)
# ---------------------------
async def _commit_refresh(session: AsyncSession, instance):
    session.add(instance)
    await session.commit()
    await session.refresh(instance)
    return instance


# ---------------------------
# BreadRepo
# ---------------------------
class BreadRepo:
    def __init__(self, db: AsyncSession):
        self.db = db

    async def get_all(self) -> List[Bread]:
        q = await self.db.execute(select(Bread))
        return q.scalars().all()

    async def get(self, bread_id: int) -> Optional[Bread]:
        q = await self.db.execute(select(Bread).where(Bread.id == bread_id))
        return q.scalar_one_or_none()

    async def create(self, brand: str, wheatLevel: int, price: float) -> Bread:
        bread = Bread(brand=brand, wheatLevel=wheatLevel, price=price)
        return await _commit_refresh(self.db, bread)

    async def update(self, bread_id: int, **fields) -> Optional[Bread]:
        obj = await self.get(bread_id)
        if not obj:
            return None
        for k, v in fields.items():
            if v is not None and hasattr(obj, k):
                setattr(obj, k, v)
        return await _commit_refresh(self.db, obj)

    async def delete(self, bread_id: int) -> bool:
        obj = await self.get(bread_id)
        if not obj:
            return False
        await self.db.delete(obj)
        await self.db.commit()
        return True


# ---------------------------
# PeanutButterRepo
# ---------------------------
class PeanutButterRepo:
    def __init__(self, db: AsyncSession):
        self.db = db

    async def get_all(self) -> List[PeanutButter]:
        q = await self.db.execute(select(PeanutButter))
        return q.scalars().all()

    async def get(self, pb_id: int) -> Optional[PeanutButter]:
        q = await self.db.execute(select(PeanutButter).where(PeanutButter.id == pb_id))
        return q.scalar_one_or_none()

    async def create(self, brand: str, isCrunchy: bool, price: float) -> PeanutButter:
        pb = PeanutButter(brand=brand, isCrunchy=isCrunchy, price=price)
        return await _commit_refresh(self.db, pb)

    async def update(self, pb_id: int, **fields) -> Optional[PeanutButter]:
        obj = await self.get(pb_id)
        if not obj:
            return None
        for k, v in fields.items():
            if v is not None and hasattr(obj, k):
                setattr(obj, k, v)
        return await _commit_refresh(self.db, obj)

    async def delete(self, pb_id: int) -> bool:
        obj = await self.get(pb_id)
        if not obj:
            return False
        await self.db.delete(obj)
        await self.db.commit()
        return True


# ---------------------------
# JellyRepo
# ---------------------------
class JellyRepo:
    def __init__(self, db: AsyncSession):
        self.db = db

    async def get_all(self) -> List[Jelly]:
        q = await self.db.execute(select(Jelly))
        return q.scalars().all()

    async def get(self, jelly_id: int) -> Optional[Jelly]:
        q = await self.db.execute(select(Jelly).where(Jelly.id == jelly_id))
        return q.scalar_one_or_none()

    async def create(self, brand: str, flavor: str, price: float) -> Jelly:
        j = Jelly(brand=brand, flavor=flavor, price=price)
        return await _commit_refresh(self.db, j)

    async def update(self, jelly_id: int, **fields) -> Optional[Jelly]:
        obj = await self.get(jelly_id)
        if not obj:
            return None
        for k, v in fields.items():
            if v is not None and hasattr(obj, k):
                setattr(obj, k, v)
        return await _commit_refresh(self.db, obj)

    async def delete(self, jelly_id: int) -> bool:
        obj = await self.get(jelly_id)
        if not obj:
            return False
        await self.db.delete(obj)
        await self.db.commit()
        return True


# ---------------------------
# SandwichRepo
# ---------------------------
class SandwichRepo:
    def __init__(self, db: AsyncSession):
        self.db = db

    async def get_all(self) -> List[PbjSandwich]:
        q = await self.db.execute(select(PbjSandwich))
        return q.scalars().all()

    async def get(self, sandwich_id: int) -> Optional[PbjSandwich]:
        q = await self.db.execute(select(PbjSandwich).where(PbjSandwich.id == sandwich_id))
        return q.scalar_one_or_none()

    async def create(self, customer: str, bread1_id: int, pb_id: int, jelly_id: int, bread2_id: int, totalCost: float) -> PbjSandwich:
        s = PbjSandwich(
            customer=customer,
            bread1_id=bread1_id,
            pb_id=pb_id,
            jelly_id=jelly_id,
            bread2_id=bread2_id,
            totalCost=totalCost
        )
        return await _commit_refresh(self.db, s)

    async def create_from_components(self, customer: str, bread1: Bread, pb: PeanutButter, jelly: Jelly, bread2: Bread) -> PbjSandwich:
        """
        Convenience: create a sandwich from ORM component objects and compute totalCost here.
        Caller must fetch the component ORM objects (bread1, pb, jelly, bread2) before calling.
        """
        total = float(bread1.price) + float(pb.price) + float(jelly.price) + float(bread2.price)
        s = PbjSandwich(
            customer=customer,
            bread1_id=bread1.id,
            pb_id=pb.id,
            jelly_id=jelly.id,
            bread2_id=bread2.id,
            totalCost=total
        )
        return await _commit_refresh(self.db, s)

    async def update(self, sandwich_id: int, **fields) -> Optional[PbjSandwich]:
        obj = await self.get(sandwich_id)
        if not obj:
            return None
        # Update scalar fields only. If component ids change, caller may want to recompute totalCost.
        for k, v in fields.items():
            if v is not None and hasattr(obj, k):
                setattr(obj, k, v)
        return await _commit_refresh(self.db, obj)

    async def delete(self, sandwich_id: int) -> bool:
        obj = await self.get(sandwich_id)
        if not obj:
            return False
        await self.db.delete(obj)
        await self.db.commit()
        return True