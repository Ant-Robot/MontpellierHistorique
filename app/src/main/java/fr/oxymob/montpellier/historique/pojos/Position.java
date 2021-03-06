package fr.oxymob.montpellier.historique.pojos;

import com.google.android.gms.maps.model.LatLng;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.Serializable;

public class Position implements Serializable, Comparable<Position>{
    private String fid; 					// r�f�rence
    private String monument;				// nom du Monument
    private String desc;					// contenu �ditorial - description
    private String adresse;					// adresse du monument (ou POI)
    private double distance;
    private String epoque = "";
    private String imageURL;
	private Point point;

	public class Point implements  Serializable {
		private double latitude;
		private double longitude;
		private double altitude;

		public Point(double lat, double lon) {
			this.latitude = lat;
			this.longitude = lon;
			this.altitude = 50.0001d;
		}

		public JSONObject toJSONString() throws JSONException {
			JSONObject object = new JSONObject();
			object.put("latitude", this.latitude);
			object.put("longitude", this.longitude);
			object.put("altitude", this.altitude);
			return object;
		}

        public LatLng toLatLng() {
            return new LatLng(latitude, longitude);
        }
	}

	public static double HaverSineDistance(double lat1, double lng1, double lat2, double lng2) {
	    lat1 = Math.toRadians(lat1);
	    lng1 = Math.toRadians(lng1);
	    lat2 = Math.toRadians(lat2);
	    lng2 = Math.toRadians(lng2);

	    double dlon = lng2 - lng1;
	    double dlat = lat2 - lat1;

	    double a = Math.pow((Math.sin(dlat/2)),2) + Math.cos(lat1) * Math.cos(lat2) * Math.pow(Math.sin(dlon/2),2);

	    double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));

	    return 6368000.0 * c;
	}   

	@Override
	public int compareTo(Position another) {
		return (int) (this.distance - another.distance);
	}

	public JSONObject toJSONObject() throws JSONException	{
		JSONObject object = new JSONObject();
		object.put("id", this.fid);
		object.put("name", this.monument);
		object.put("description", this.desc);
		object.put("type", 1);
		object.put("Point", this.point.toJSONString());
		return object;
	}

    public void copyFromMonument(Monument monument, double userLat, double userLg) throws Exception {
        setAdresse(monument.getAdresse());
        setDesc(monument.getAdresse() + "\n" + monument.getEpoque());
        setEpoque(monument.getEpoque());
        setFid(monument.getFid());
        setMonument(monument.getMonument());
        setImageURI(monument.getVignetteUri());
        setPoint(new Point(Double.parseDouble(monument.getLat()), Double.parseDouble(monument.getLg())));
        setDistance(HaverSineDistance(getPoint().latitude, getPoint().longitude, userLat, userLg));
    }

	public String toString() {
		String ret = "";

		ret = fid + "\n";
		ret = monument + "\n";
		ret = desc + "\n";
		ret = adresse + "\n";

		return ret;
	}
	
	public String getStrDistance() {
		String strDistance="";
		if (distance!=0) {
			if (distance > 5000)
				strDistance = String.format("%5.1f km", distance/1000);
			else
				strDistance = String.format("%4.0f mètres", distance);
		}
		return strDistance;
	}

    public String getFid() {
        return fid;
    }

    public String getMonument() {
        return monument;
    }



    public String getDesc() {
        return desc;
    }

    public String getAdresse() {
        return adresse;
    }

    public double getDistance() {
        return distance;
    }

    public String getEpoque() {
        return epoque;
    }

    public String getImageURL() {
        return imageURL;
    }

    public Point getPoint() {
        return point;
    }

    public void setFid(String fid) {
        this.fid = fid;
    }

    public void setMonument(String monument) {
        this.monument = monument;
    }


    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public void setEpoque(String epoque) {
        this.epoque = epoque;
    }

    public void setImageURI(String imageURL) {
        this.imageURL = imageURL;
    }

    public void setPoint(Point point) {
        this.point = point;
    }
}
