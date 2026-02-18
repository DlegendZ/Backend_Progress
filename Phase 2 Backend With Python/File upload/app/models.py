from sqlalchemy import Column, Integer, LargeBinary
from app.database import Base

class Image(Base):
    __tablename__ = "images"

    id = Column(Integer, primary_key=True, index=True)
    data = Column(LargeBinary, nullable=False)
