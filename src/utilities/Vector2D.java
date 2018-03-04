package utilities;

public final class Vector2D {
    public double x, y;

    public Vector2D() {
        this.x = 0;
        this.y = 0;
    }

    public Vector2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Vector2D(Vector2D v){
        this.x = v.x;
        this.y = v.y;
    }

    public Vector2D set(double x, double y){
        this.x = x;
        this.y = y;
        return this;
    }

    public Vector2D set(Vector2D v){
        this.x = v.x;
        this.y = v.y;
        return this;
    }

    public boolean equals(Object o){
        if (getClass() == o.getClass()){
            Vector2D v = (Vector2D) o;
            return this.x == v.x && this.y == v.y;
        }
         return false;
    }

    public String toString(){
        return this.x + " , " + this.y;
    }

    public double mag(){
        return Math.hypot(this.x, this.y);
    }

    public double angle(){
        return Math.atan2(this.y, this.x);
    }

    public double angle(Vector2D other){
        double angle1 = Math.atan2(this.y, this.x);
        double angle2 = Math.atan2(other.y, other.x);
        double differ = angle2 - angle1;
        if (differ > Math.PI){
            differ -= Math.PI * 2;
//            return differ;
        }
        if (differ < -Math.PI){
            differ += 2 * Math.PI;
//            return Math.PI * 2 + differ;
        }
        return differ;
//        return Math.atan2(this.y - other.y, this.x - other.x);
    }

    public Vector2D add(Vector2D v){
        this.x += v.x;
        this.y += v.y;
        return this;
    }

    public Vector2D add(double x, double y){
        this.x += x;
        this.y += y;
        return this;
    }

    public Vector2D addScaled(Vector2D v, double fac){
        this.x += fac * (v.x);
        this.y += fac * (v.y);
        return this;
    }

    public Vector2D subtract(double x, double y){
        this.x -= x;
        this.y -= y;
        return this;
    }

    public Vector2D subtract(Vector2D v){
        this.x -= v.x;
        this.y -= v.y;
        return this;
    }

    public Vector2D mult(double fac){
        this.x *= fac;
        this.y *= fac;
        return this;
    }

    public Vector2D rotate(double angle){
        double mag = this.mag();
        double newangle = this.angle() + angle;
        this.x = mag * Math.cos(newangle);
        this.y = mag * Math.sin(newangle);
        return this;
    }

    public double dot(Vector2D v){
        return this.x * v.x + this.y * v.y;
    }

    public double dist(Vector2D v){
        return Math.sqrt((this.x - v.x) * (this.x - v.x) + (this.y - v.y) * (this.y - v.y));
    }

    public Vector2D normalise(){
        double mag = this.mag();
        this.x /= mag;
        this.y /= mag;
        return this;
    }

    public Vector2D wrap(double w, double h){
        if (this.x > w){
            this.x %= w;
        }
        if (this.x < 0){
            this.x = w + this.x % w;
        }
        if (this.y > h){
            this.y %= h;
        }
        if (this.y < 0){
            this.y = h + this.y % h;
        }
        return this;
    }

    public static Vector2D polar(double angle, double mag){
        return new Vector2D(mag * Math.cos(angle), mag * Math.sin(angle));
    }

}












