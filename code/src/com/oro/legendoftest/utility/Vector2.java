package com.oro.legendoftest.utility;


public class Vector2 
{
	static Vector2 tmp;
	static Vector2 tmp2;
	static Vector2 tmp3;
	static Vector2 N = new Vector2(0, -1);
	static Vector2 NE = new Vector2(Constants.ONE_OVER_SQRT_TWO, -Constants.ONE_OVER_SQRT_TWO);
	static Vector2 E = new Vector2(1, 0);
	static Vector2 SE = new Vector2(Constants.ONE_OVER_SQRT_TWO, Constants.ONE_OVER_SQRT_TWO);
	static Vector2 S = new Vector2(0, 1);
	static Vector2 SW = new Vector2(-Constants.ONE_OVER_SQRT_TWO, Constants.ONE_OVER_SQRT_TWO);
	static Vector2 W = new Vector2(-1, 0);
	static Vector2 NW = new Vector2(-Constants.ONE_OVER_SQRT_TWO, -Constants.ONE_OVER_SQRT_TWO);
	static Vector2 Zero = new Vector2();
	
	
	float xComp;
	float yComp;
	
	//*************************************
	// Constructors
	//*************************************
	public Vector2()
	{
		xComp = 0f;
		yComp = 0f;
	}
	public Vector2(float x, float y)
	{
		xComp = x;
		yComp = y;	
	}
	public Vector2(Vector2 v)
	{
		xComp = v.xComp;
		yComp = v.yComp;
	}
	
	
	public Vector2 add(float x, float y)
	{
		xComp += x;
		yComp += y;
		return this;
	}	
	public Vector2 add(Vector2 v)
	{
		xComp += v.xComp;
		yComp += v.yComp;
		return this;
	}
	public Vector2 subtract(float x, float y)
	{
		xComp -= x;
		yComp -= y;
		return this;
	}	
	public Vector2 subtract(Vector2 v)
	{
		xComp -= v.xComp;
		yComp -= v.yComp;
		return this;
	}
	
	public float Angle()
	{
		float angle;
		if(xComp != 0)
		{
			angle = (float) Math.atan(yComp/xComp);
		}
		else
		{
			angle = 0;
		}
		return angle;
	}
	
	public Vector2 copy()
	{
		Vector2 vector = new Vector2(this);
		return vector;
	}
	
	public Vector2 divide(float i)
	{
		xComp /= i;
		yComp /= i;
		
		return this;
	}
	public Vector2 divide(float x, float y)
	{
		xComp /= x;
		yComp /= y;
		
		return this;
	}
	public Vector2 divide(Vector2 v)
	{
		xComp /= v.xComp;
		yComp /= v.yComp;
		
		return this;
	}
	public float dotProduct(Vector2 v)
	{
		float product = xComp*v.xComp + yComp*v.yComp;
		return product;
	}
	public float distance(float x, float y)
	{
		float xDistance = Math.abs(xComp - x);
		float yDistance = Math.abs(yComp - y);
		
		float distance = (float) Math.sqrt(yDistance*yDistance + xDistance*xDistance);	
		return distance;
	}
	public float distance(Vector2 v)
	{
		float xDistance = Math.abs(xComp - v.xComp);
		float yDistance = Math.abs(yComp - v.xComp);
		
		float distance = (float) Math.sqrt(yDistance*yDistance + xDistance*xDistance);	
		return distance;
	}
	public float distanceSquared(float x, float y)
	{
		float xDistance = Math.abs(xComp - x);
		float yDistance = Math.abs(yComp - y);
		
		float distance = yDistance*yDistance + xDistance*xDistance;	
		return distance;
	}
	public float distanceSquared(Vector2 v)
	{
		float xDistance = Math.abs(xComp - v.xComp);
		float yDistance = Math.abs(yComp - v.xComp);
		
		float distance = yDistance*yDistance + xDistance*xDistance;	
		return distance;
	}
	public boolean vectorEquals(Vector2 v)
	{
		float epsilon = 0.000001f;
		if(xComp > v.xComp - epsilon && xComp < v.xComp + epsilon)
		{
			if(yComp > v.yComp - epsilon && yComp < v.yComp + epsilon)
			{
				return true;
			}
		}
		return false;
	}
	@Override
	public boolean equals(java.lang.Object obj)
	{		
		if(obj == this)
		{
			return true;
		}
		if(obj == null || obj.getClass() != this.getClass())
		{
			return false;
		}
		
		Vector2 v = (Vector2)obj;
		
		return vectorEquals(v);
	}
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = (prime * result) + Float.floatToIntBits(xComp);
		result = (prime * result) + Float.floatToIntBits(yComp);
		return result;
	}
	public float length()
	{
		float length = (float) Math.sqrt(xComp * xComp + yComp * yComp);
		return length;
	}
	public float lengthSquared()
	{
		float length = xComp * xComp + yComp * yComp;
		return length;
	}
	public Vector2 lerp(Vector2 target, float alpha)
	{
		float newXcomp = Math.abs(target.xComp - xComp) * alpha;
		float newYcomp = Math.abs(target.yComp - yComp) * alpha;
		Vector2 newVector = new Vector2(newXcomp, newYcomp);
		return newVector;
	}
	public Vector2 multiply(float i)
	{
		xComp *= i;
		yComp *= i;
		return this;
	}
	public Vector2 multiply(float x, float y)
	{
		xComp *= x;
		yComp *= y;
		
		return this;
	}
//	public Vector2 multiply(Matrix3 m*)
//	{
// 
//	}
	public Vector2 normalize() throws ArithmeticException
	{
		if (lengthSquared() != 0)
		{
			xComp = xComp / length();
			yComp = yComp / length();
			return this;
		}
		else
		{
			throw new ArithmeticException("Cannot normalize a Vector with length 0");
		}
	}
	public Vector2 rotate(float degrees)
	{
		float angle = Angle() + degrees;
		xComp = (float) Math.sin(angle) * length();
		yComp = (float) Math.cos(angle) * length();
		return this;
	}
	public Vector2 set(float x, float y)
	{
		xComp = x;
		yComp = y;
		return this;
	}
	public Vector2 set(Vector2 v)
	{
		xComp = v.xComp;
		yComp = v.yComp;
		return this;
	}
	public Vector2 setAngle(float angle)
	{
		xComp = (float) Math.sin(angle) * length();
		yComp = (float) Math.cos(angle) * length();
		return this;
	}
	public float getX()
	{
		return xComp;
	}
	public float getY()
	{
		return yComp;
	}
	public Vector2 tmp()
	{
		tmp = this;
		return tmp;
	}
	public Vector2 tmp2()
	{
		tmp2 = this;
		return tmp2;
	}
	public Vector2 tmp3()
	{
		tmp3 = this;
		return tmp3;
	}
	@Override
	public String toString()
	{
		return "Vector2: X=" + xComp + " Y=" + yComp;
	}
}