package com.project.fyp.threads;

public class CallingSites {

    public void callingmain(String searchtext){
        ThreadCallGeneral snap = new ThreadCallGeneral("https://www.snapdeal.com/search?keyword=" + searchtext + "&santizedKeyword=&catId=" +
                "&categoryId=0&suggested=true&vertical=&noOfResults=20&searchState=&clickSrc=suggested&lastKeyword=&prodCatId=&change" +
                "BackToAll=false&foundInAll=false&categoryIdSearched=&cityPageUrl=&categoryUrl=&url=&utmContent=&dealDetail=&sort=rlvncy","Snapdeal");
        ThreadCallGeneral shop = new ThreadCallGeneral("https://www.shopclues.com/search?q=" +searchtext +"&sc_z=2222&z=0&count=10", "ShopClues");
        ThreadCallGeneral pay = new ThreadCallGeneral("https://www.paytmmall.com/shop/search?q=" +searchtext+ "&from=organic&child_site_id=6","Paytm");
        snap.start();
        shop.start();
        pay.start();
    }

    public void callingfashion(String searchtext){

        ThreadCallFashion bew = new ThreadCallFashion("https://www.bewakoof.com/search/" + searchtext + "?ga_q=" + searchtext, "Bewakoof");
        bew.start();

        ThreadCallFashion pay = new ThreadCallFashion("https://www.paytmmall.com/shop/search?q=" +searchtext+ "&from=organic&child_site_id=6","Paytm");
        pay.start();

        ThreadCallFashion snap = new ThreadCallFashion("https://www.snapdeal.com/search?keyword=" + searchtext + "&santizedKeyword=&catId=" +
                "&categoryId=0&suggested=true&vertical=&noOfResults=20&searchState=&clickSrc=suggested&lastKeyword=&prodCatId=&change" +
                "BackToAll=false&foundInAll=false&categoryIdSearched=&cityPageUrl=&categoryUrl=&url=&utmContent=&dealDetail=&sort=rlvncy","Snapdeal" );
        snap.start();

        ThreadCallFashion shop = new ThreadCallFashion("https://www.shopclues.com/search?q=" +searchtext +"&sc_z=2222&z=0&count=10", "ShopClues");
        shop.start();

        ThreadCallFashion koov = new ThreadCallFashion("https://www.koovs.com/" + searchtext, "Koovs");
        koov.start();
    }

    public void callingelectronics(String searchtext){
        ThreadCallFashion pay = new ThreadCallFashion("https://www.paytmmall.com/shop/search?q=" +searchtext+ "&from=organic&child_site_id=6","Paytm");
        pay.start();

        ThreadCallFashion snap = new ThreadCallFashion("https://www.snapdeal.com/search?keyword=" + searchtext + "&santizedKeyword=&catId=" +
                "&categoryId=0&suggested=true&vertical=&noOfResults=20&searchState=&clickSrc=suggested&lastKeyword=&prodCatId=&change" +
                "BackToAll=false&foundInAll=false&categoryIdSearched=&cityPageUrl=&categoryUrl=&url=&utmContent=&dealDetail=&sort=rlvncy","Snapdeal" );
        snap.start();

        ThreadCallFashion shop = new ThreadCallFashion("https://www.shopclues.com/search?q=" +searchtext +"&sc_z=2222&z=0&count=10", "ShopClues");
        shop.start();


    }

}
