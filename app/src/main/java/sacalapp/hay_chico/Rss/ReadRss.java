package sacalapp.hay_chico.Rss;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Created by jarvicfelipe on 27/02/2017.
 */

public class ReadRss extends AsyncTask<Void, Void, Void> {
    Context context;
    String address = "https://futbol.as.com/rss/futbol/internacional.xml";
    String address2 = "http://sacalapp.com/feed/";
   //String address = "http://www.sciencemag.org/rss/news_current.xml";
    ProgressDialog progressDialog;
    ArrayList<FeedItem>feedItems;
    ArrayList<FeedItem>feedItems2;
    ArrayList<FeedItem>final_;

    RecyclerView recyclerView;
    URL url,url2;
    int Cantidad;



    public ReadRss(Context context,RecyclerView recyclerView) {
        this.recyclerView=recyclerView;
        this.context = context;
       progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Cargando Noticias...");
    }

    @Override
    protected void onPreExecute() {
        progressDialog.show();
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        progressDialog.dismiss();
        Agrupar();


    }

    private void Agrupar() {

        int list1=0,list2=0,i=0;
        final_=new ArrayList<>();
        Cantidad=feedItems.size()+feedItems2.size();

        while (i<Cantidad){


            if ( list1<feedItems2.size() ) {
                final_.add(feedItems2.get(list1));
                list1=list1+1;
                i++;
                }

            if ( list2<feedItems.size() ) {

                if (list2 <=feedItems.size()-5) {
                    int numero = (int) (Math.random() * 3) + 1;
                    for (int j = 0; j <numero ; j++) {
                        final_.add(feedItems.get(list2));
                        list2=list2+1;
                        i++;
                    }
                }else {
                    final_.add(feedItems.get(list2));
                    list2=list2+1;
                    i++;
                }
            }

        }


        MyAdapter adapter=new MyAdapter(context,final_);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
      //  recyclerView.addItemDecoration(new VerticalSpace(50));
        recyclerView.setAdapter(adapter);

    }

    @Override
    protected Void doInBackground(Void... params) {
        ProcessXml(Getdata());
        ProcessXml2(Getdata2());
        return null;
    }

    private void ProcessXml(Document data) {
        if (data != null) {
            feedItems=new ArrayList<>();
            feedItems.clear();
            Element root = data.getDocumentElement();
            Node channel = root.getChildNodes().item(1);
            NodeList items = channel.getChildNodes();
            for (int i = 0; i < items.getLength(); i++) {
                Node cureentchild = items.item(i);
               if (cureentchild.getNodeName().equalsIgnoreCase("item")) {
                    FeedItem item=new FeedItem();
                    NodeList itemchilds = cureentchild.getChildNodes();

                    for (int j = 0; j < itemchilds.getLength(); j++) {
                       Node cureent = itemchilds.item(j);
                        Log.d("item",cureent.getTextContent());
                       if (cureent.getNodeName().equalsIgnoreCase("title")){
                            item.setTitle(cureent.getTextContent());
                        }else if (cureent.getNodeName().equalsIgnoreCase("description")){
                            item.setDescription(cureent.getTextContent());
                        }else if (cureent.getNodeName().equalsIgnoreCase("pubDate")){
                            item.setPubDate(cureent.getTextContent());
                        }else if (cureent.getNodeName().equalsIgnoreCase("link")){
                            item.setLink(cureent.getTextContent());
                        }else if (cureent.getNodeName().equalsIgnoreCase("enclosure")){
                            //this will return us thumbnail url
                            String url=cureent.getAttributes().item(0).getTextContent();
                            item.setThumbnailUrl(url);

                        }
                    }
                    feedItems.add(item);
                    //Log.d("imegen",item.getThumbnailUrl());

                }
            }
        }
    }

    private void ProcessXml2(Document data) {
        if (data != null) {
            feedItems2=new ArrayList<>();
            feedItems2.clear();
            Element root = data.getDocumentElement();
            Node channel = root.getChildNodes().item(1);
            NodeList items = channel.getChildNodes();
            for (int i = 0; i < items.getLength(); i++) {
                Node cureentchild = items.item(i);
                if (cureentchild.getNodeName().equalsIgnoreCase("item")) {
                    FeedItem item=new FeedItem();
                    NodeList itemchilds = cureentchild.getChildNodes();

                    for (int j = 0; j < itemchilds.getLength(); j++) {
                        Node cureent = itemchilds.item(j);
                        Log.d("item",cureent.getTextContent());
                        if (cureent.getNodeName().equalsIgnoreCase("title")){
                            item.setTitle(cureent.getTextContent());
                        }else if (cureent.getNodeName().equalsIgnoreCase("description")){
                            item.setDescription(cureent.getTextContent());
                        }else if (cureent.getNodeName().equalsIgnoreCase("pubDate")){
                            item.setPubDate(cureent.getTextContent());
                        }else if (cureent.getNodeName().equalsIgnoreCase("link")){
                            item.setLink(cureent.getTextContent());
                        }else if (cureent.getNodeName().equalsIgnoreCase("media:content")){
                            //this will return us thumbnail url
                            String url=cureent.getAttributes().item(0).getTextContent();
                            item.setThumbnailUrl(url);

                        }
                    }
                    feedItems2.add(item);
                    //Log.d("imegen",item.getThumbnailUrl());

                }
            }
        }
    }

    public Document Getdata() {
        try {
            url = new URL(address);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            InputStream inputStream = connection.getInputStream();
            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = builderFactory.newDocumentBuilder();
            Document xmlDoc = builder.parse(inputStream);
            return xmlDoc;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Document Getdata2() {
        try {
            url2 = new URL(address2);
            HttpURLConnection connection = (HttpURLConnection) url2.openConnection();
            connection.setRequestMethod("GET");
            InputStream inputStream = connection.getInputStream();
            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = builderFactory.newDocumentBuilder();
            Document xmlDoc = builder.parse(inputStream);
            return xmlDoc;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


}