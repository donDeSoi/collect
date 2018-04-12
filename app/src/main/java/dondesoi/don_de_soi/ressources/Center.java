package dondesoi.don_de_soi.ressources;

import com.google.android.gms.maps.model.LatLng;

public class Center {
    //private LatLng latLng;
    //private String data;
    private int id;
    private Double lat,lng;
    private String address,intel,access,donation;

    public int getId() {
        return id;
    }
    public Double getLat() {
        return lat;
    }
    public Double getLng() {
        return lng;
    }
    public String getAddress() {
        return address;
    }
    public String getIntel(){
        return intel;
    }
    public String getAccess(){
        return access;
    }
    public String getDonation() {
        return donation;
    }

    public Center(int id, Double lat, Double lng, String address, String donation, String intel, String access){
        // this.latLng = latLng;
        //this.data = data;
        this.id = id;
        this.lat = lat;
        this.lng = lng;
        this.address = address;
        this.donation = donation;
        this.intel = intel;
        this.access = access;
    }

    public void display(){
        System.out.println(this.getId());
        System.out.println(this.getLat());
        System.out.println(this.getLng());
        System.out.println(this.getAddress());
        System.out.println(this.getDonation());
        System.out.println(this.getIntel());
        System.out.println(this.getAccess());
    }


    public void center_DB(){
        //int ID = 1;
        int ID;
        double lat,lng;

        //while (ID<=1){
        // INSERTING DATA INSIDE DATABASE
        ID = 1;
        String  address = "Hopital Pitie Salpetriere 47 Bd Hopital 75013 Paris 13eme";
        String  donation = "Sang plasma plaquettes";
        String intel = "du lundi au samedi de 9h à 15h30";
        String access = "0142160252 \n" +
                "Chevaleret (ligne 6) ou Saint-Marcel (ligne 5)\n" +
                "Bus 57, 91, 27";
        lat = 48.837079;
        lng = 2.365043;

        //SaveData.insertIntoDatabase(center_database,"CENTER_INTEL VALUES ("+ ID + "," + lat + "," + lng + "," + address + "," + donation + "," + intel + "," + access + ")");

            /*Scanner scan = new Scanner(System.in);
            //BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("ADDRESS : ");
            String address = scan.next();
            System.out.println("DONATION TYPE : ");
            String donation = scan.next();
            System.out.println("HORAIRES : ");
            String intel = scan.next();
            System.out.println("ACCESS (tel etc) : ");
            String access = scan.next();
            System.out.println("LATITUDE : ");
            lat = scan.nextDouble();
            System.out.println("LONGITUDE : ");
            lng = scan.nextDouble();


            SaveData.insertIntoDatabase(center_database,"CENTER_INTEL VALUES ("+ ID +","+ lat + "," + lng + "," + address + "," + donation + "," + intel + "," + access + ")");
            Cursor cursor = SaveData.fetchDatabase(center_database,"SELECT id,long,lat,address,donation,intel,access FROM CENTER_INTEL where id=" + ID + ";");

            if(cursor!= null){
                if(cursor.getCount() > 0){
                    do{
                        //read
                        System.out.println("ID : " + cursor.getInt(0));
                        System.out.println("LONG : " + cursor.getDouble(1));
                        System.out.println("LAT : " + cursor.getDouble(2));
                        System.out.println("ADDRESS : " + cursor.getString(3));
                        System.out.println("DONATION : " + cursor.getString(4));
                        System.out.println("INTEL : " + cursor.getString(5));
                        System.out.println("ACCESS : " + cursor.getString(6));
                    }while(cursor.moveToNext());
                }
            }

            ID=ID + 1;
        }*/

    }

    public String getData(){
        String result = "";
        if(!address.isEmpty()){
            result += address + "\n";
        }
        if(!donation.isEmpty()){
            result += donation + "\n";
        }
        if(!intel.isEmpty()){
            result += intel + "\n";
        }
        if(!access.isEmpty()){
            result += access + "\n";
        }
        return result;
    }
    public LatLng getLatLng(){
        LatLng latLng = null;
        if(lat != null && lng != null){
            latLng = new LatLng(lat, lng);
        }
        return latLng;
    }
    @Override
    public String toString(){
        return "id : "+ id +" latitude : "+ lat + " longitude : "+ lng + " address : "+ address + " donation : "+ donation +" intel : "+ intel
                +" access : "+ access;
    }

      /*  public void center_DB(){
            int ID = 1;
            double lat,lng;
            String address,donation,intel,access;

            while (ID<=1){
            // INSERTING DATA INSIDE DATABASE

            // FOR HERE
            //address = "Hôpital Pitié-Salpétrière 47 Bd Hôpital Paris";
            //donation = "Sang, plasma, plaquettes";
            //intel = "du lundi au samedi de 9h à 15h30";
            //access = "0142160252\n" +
              //      "Chevaleret (ligne 6) ou Saint-Marcel (ligne 5)\n" +
                //    "Bus 57, 91, 27";
            //lat = 48.837079;
            //lng = 2.365043;
            //SaveData.insertIntoDatabase(center_database,"CENTER_INTEL VALUES ("+ ID +","+ lat + "," + lng + "," + address + "," + donation + "," + intel + "," + access + ")";

            Scanner scan = new Scanner(System.in);
            System.out.println("ADDRESS : ");
            address = scan.next();
            System.out.println("DONATION TYPE : ");
            donation = scan.next();
            System.out.println("HORAIRES : ");
            intel = scan.next();
            System.out.println("ACCESS (tel etc) : ");
            access = scan.next();
            System.out.println("LATITUDE : ");
            lat = scan.nextDouble();
            System.out.println("LONGITUDE : ");
            lng = scan.nextDouble();


            SaveData.insertIntoDatabase(center_database,"CENTER_INTEL VALUES ("+ ID +","+ lat + "," + lng + "," + address + "," + donation + "," + intel + "," + access + ")");
            Cursor cursor = SaveData.fetchDatabase(center_database,"SELECT id,long,lat,address,donation,intel,access FROM CENTER_INTEL where id=" + ID + ";");

                if(cursor!= null){
                    if(cursor.getCount() > 0){
                        do{
                            //read
                            System.out.println("ID : " + cursor.getInt(0));
                            System.out.println("LONG : " + cursor.getDouble(1));
                            System.out.println("LAT : " + cursor.getDouble(2));
                            System.out.println("ADDRESS : " + cursor.getString(3));
                            System.out.println("DONATION : " + cursor.getString(4));
                            System.out.println("INTEL : " + cursor.getString(5));
                            System.out.println("ACCESS : " + cursor.getString(6));
                        }while(cursor.moveToNext());
                    }
                }

            ID=ID + 1;
            }

        }*/
}