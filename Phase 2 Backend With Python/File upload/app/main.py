from fastapi import UploadFile, File, Depends, FastAPI, HTTPException, Response
from sqlalchemy.orm import Session
from app.database import get_db
from app.models import Image
from fastapi.responses import StreamingResponse
import io
import os

app = FastAPI()

# @app.post("/upload")
# async def upload_image(file: UploadFile = File(...), db: Session = Depends(get_db)):
#     content = await file.read()

#     image = Image(data=content)
#     db.add(image)
#     db.commit()
#     db.refresh(image)

#     return {"id": image.id}

UPLOAD_DIR = "uploads"

@app.post("/upload")
async def upload_image(file: UploadFile = File(...), db: Session = Depends(get_db)):

    os.makedirs(UPLOAD_DIR, exist_ok=True)

    file_location = f"{UPLOAD_DIR}/{file.filename}"

    with open(file_location, "wb") as f:
        content = await file.read()
        f.write(content)

    image = Image(file_path=file_location)
    db.add(image)
    db.commit()
    db.refresh(image)

    return {"file_path": file_location}

@app.get("/images/{image_id}")
def get_image(image_id: int, db: Session = Depends(get_db)):
    
    image = db.query(Image).filter(Image.id == image_id).first()

    if not image:
        raise HTTPException(status_code=404, detail="Image not found")

    return Response(
        content=image.data,
        media_type="image/png"   # hardcode temporarily
    )


