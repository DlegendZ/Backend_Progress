from fastapi import Depends, HTTPException
from fastapi.security import OAuth2PasswordBearer
from sqlalchemy.orm import Session
from app.database import get_db
from app.security import decode_token
from app.models import User

oauth2 = OAuth2PasswordBearer(tokenUrl="/login")

def get_current_user(
    token: str = Depends(oauth2),
    db: Session = Depends(get_db)
):
    payload = decode_token(token)
    user_id = payload.get("sub")

    user = db.query(User).filter(User.id == int(user_id)).first()
    if not user:
        raise HTTPException(401, "User not found")
    return user

def require_admin(
    user: User = Depends(get_current_user)
):
    if user.role != "admin":
        raise HTTPException(403, "Admin access required")
    return user