from fastapi import FastAPI, Depends
from sqlalchemy.orm import Session
from app.database import get_db
from app.dependencies import get_current_user, require_admin
from app.services import (
    create_task,
    update_task,
    add_project_member
)

app = FastAPI()

@app.get("/admin/dashboard")
def admin_dashboard(user=Depends(require_admin)):
    return {"message": "Admin only"}

@app.put("/tasks/{task_id}")
def edit_task(
    task_id: int,
    title: str,
    db: Session = Depends(get_db),
    user=Depends(get_current_user)
):
    return update_task(db, task_id, title, user)

@app.post("/projects/{project_id}/members/{user_id}")
def add_member(
    project_id: int,
    user_id: int,
    db: Session = Depends(get_db),
    user=Depends(get_current_user)
):
    add_project_member(db, project_id, user_id, user)
    return {"message": "Member added"}