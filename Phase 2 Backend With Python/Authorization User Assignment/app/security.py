from jose import jwt, JWTError
from passlib.context import CryptContext
from fastapi import HTTPException, status
from datetime import datetime, timedelta

SECRET = "SECRET"
ALGO = "HS256"

pwd = CryptContext(schemes=["bcrypt"])

def hash_password(p):
    return pwd.hash(p)

def verify_password(p, h):
    return pwd.verify(p, h)

def create_token(user_id: int):
    payload = {
        "sub": str(user_id),
        "exp": datetime.utcnow() + timedelta(minutes=15)
    }

    return jwt.encode(payload, SECRET, algorithm=ALGO)

def decode_token(token: str):
    try:
        return jwt.decode(token, SECRET, algorithms=[ALGO])
    except JWTError:
        raise HTTPException(401, "Invalid or expired token")
