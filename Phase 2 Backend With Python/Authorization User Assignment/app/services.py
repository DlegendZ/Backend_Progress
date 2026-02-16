from fastapi import HTTPException
from app.models import Project, Task, User

def add_project_member(db, project_id, user_id, current_user):
    project = db.query(Project).filter(
        Project.id == project_id
    ).first()

    if not project:
        raise HTTPException(404, "Project not found")

    if current_user.role != "admin" and project.owner_id != current_user.id:
        raise HTTPException(403, "Not allowed")

    user = db.query(User).filter(User.id == user_id).first()
    if not user:
        raise HTTPException(404, "User not found")

    project.members.append(user)
    db.commit()


def check_project_access(project, user):
    if user.role == "admin":
        return
    if project.owner_id == user.id:
        return
    if user not in project.members:
        # hide existence
        raise HTTPException(404, "Project not found")

def create_task(db, project_id, title, current_user):
    project = db.query(Project).filter(
        Project.id == project_id
    ).first()

    if not project:
        raise HTTPException(404)

    check_project_access(project, current_user)

    task = Task(
        title=title,
        project_id=project.id,
        owner_id=current_user.id
    )
    db.add(task)
    db.commit()
    return task


def update_task(db, task_id, title, current_user):
    task = db.query(Task).filter(
        Task.id == task_id
    ).first()

    if not task:
        raise HTTPException(404)

    if current_user.role != "admin" and task.owner_id != current_user.id:
        raise HTTPException(403, "You cannot edit this task")

    task.title = title
    db.commit()
    return task