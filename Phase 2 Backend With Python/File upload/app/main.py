from fastapi import UploadFile, File, Depends, FastAPI, HTTPException, Response
from sqlalchemy.orm import Session
from app.database import get_db
from app.models import Image
from fastapi.responses import StreamingResponse
import io

app = FastAPI()

@app.post("/upload")
async def upload_image(file: UploadFile = File(...), db: Session = Depends(get_db)):
    content = await file.read()

    image = Image(data=content)
    db.add(image)
    db.commit()
    db.refresh(image)

    return {"id": image.id}

@app.get("/images/{image_id}")
def get_image(image_id: int, db: Session = Depends(get_db)):
    
    image = db.query(Image).filter(Image.id == image_id).first()

    if not image:
        raise HTTPException(status_code=404, detail="Image not found")

    return Response(
        content=image.data,
        media_type="image/png"   # hardcode temporarily
    )


