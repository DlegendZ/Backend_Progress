from sqlalchemy import (
    Column, Integer, String, 
    ForeignKey, Table
)
from sqlalchemy.orm import relationship
from app.database import Base

project_members = Table(
    "project_members",
    Base.metadata,
    Column("user_id", ForeignKey("users.id")),
    Column("project_id", ForeignKey("projects.id")),
)

class User(Base):
    __tablename__ = "users"

    id = Column(Integer, primary_key=True)
    email = Column(String, unique=True)
    hashed_password = Column(String)
    role = Column(String, default="user")

class Project(Base):
    __tablename__ = "projects"

    id = Column(Integer, primary_key=True)
    name = Column(String)
    owner_id = Column(Integer, ForeignKey("users.id"))

    members = relationship(
        "User", 
        secondary=project_members, 
        backref="projects"
    )

class Task(Base):
    __tablename__ = "tasks"

    id = Column(Integer, primary_key=True)
    title = Column(String)
    project_id = Column(Integer, ForeignKey("projects.id"))
    owner_id = Column(Integer, ForeignKey("users.id"))
