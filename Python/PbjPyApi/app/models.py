# app/models.py
from sqlalchemy import Column, Integer, String, Float, Boolean, ForeignKey
from sqlalchemy.orm import relationship
from .db import Base

class Bread(Base):
    __tablename__ = "bread"
    id = Column(Integer, primary_key=True, index=True)
    brand = Column(String(100), nullable=False)
    wheatLevel = Column(Integer, nullable=False)  # e.g., 0-100
    price = Column(Float, nullable=False)

class PeanutButter(Base):
    __tablename__ = "peanut_butter"
    id = Column(Integer, primary_key=True, index=True)
    brand = Column(String(100), nullable=False)
    isCrunchy = Column(Boolean, nullable=False)
    price = Column(Float, nullable=False)

class Jelly(Base):
    __tablename__ = "jelly"
    id = Column(Integer, primary_key=True, index=True)
    brand = Column(String(100), nullable=False)
    flavor = Column(String(100), nullable=False)
    price = Column(Float, nullable=False)

class PbjSandwich(Base):
    __tablename__ = "pbj_sandwich"
    id = Column(Integer, primary_key=True, index=True)
    customer = Column(String(200), nullable=False)

    bread1_id = Column(Integer, ForeignKey("bread.id"), nullable=False)
    pb_id = Column(Integer, ForeignKey("peanut_butter.id"), nullable=False)
    jelly_id = Column(Integer, ForeignKey("jelly.id"), nullable=False)
    bread2_id = Column(Integer, ForeignKey("bread.id"), nullable=False)

    totalCost = Column(Float, nullable=False)

    # relationships
    bread1 = relationship("Bread", foreign_keys=[bread1_id])
    bread2 = relationship("Bread", foreign_keys=[bread2_id])
    peanut_butter = relationship("PeanutButter", foreign_keys=[pb_id])
    jelly = relationship("Jelly", foreign_keys=[jelly_id])