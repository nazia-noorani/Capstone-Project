package nazianoorani.sportsfestapp;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.joanzapata.pdfview.PDFView;

import nazianoorani.sportsfestapp.util.EventName;

/**
 * Created by nazianoorani on 20/04/16.
 */
public class RulesFragment extends Fragment {
    int eventNo;
    PDFView pdfView;
    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_rules,container,false);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);

        setHasOptionsMenu(true);
        pdfView = (PDFView) view.findViewById(R.id.pdfview);
        Bundle bundle = getArguments();
        if(bundle !=null){
           eventNo = bundle.getInt("eventNo");
        }
        ((DetailsActivity) getActivity()).getSupportActionBar().setTitle(EventName.getEventName(eventNo)+" - Rules");
        initView();

        return view;
    }

    private void initView() {
        String pdfName = "badminton_rules.pdf";
        switch (eventNo){
            case  1 :pdfName ="badminton_rules.pdf";
                showpdf4(pdfName);
                break;
            case  2 :pdfName ="tt_rules.pdf";
                showpdf4(pdfName);
                break;
            case  3 :pdfName ="lt_rules.pdf";
                showpdf4(pdfName);
                break;
            case  4 :pdfName ="tt_rules.pdf";
                showpdf3(pdfName);
                break;
            case  5 :pdfName ="tt_rules.pdf";
                showpdf3(pdfName);
                break;
            case  6 :pdfName ="ckt_rules.pdf";
                showpdf4(pdfName);
                break;
            case  7 :pdfName ="fb_rules.pdf";
                showpdf4(pdfName);
                break;
            case  8 :pdfName ="basketball_rules.pdf";
                showpdf4(pdfName);
                break;
            case  9 :pdfName ="vb_rules.pdf";
                showpdf4(pdfName);
                break;
        }


    }

    void showpdf3(String pdfName)
    {
        pdfView.fromAsset(pdfName)
                .pages(0,1,2)
                .swipeVertical(true)
                .defaultPage(1)
                .showMinimap(false)
                .enableSwipe(true)
                .load();
    }
    void showpdf4(String pdfName)
    {
        pdfView.fromAsset(pdfName)
                .pages(0,1,2,3)
                .swipeVertical(true)
                .defaultPage(1)
                .showMinimap(false)
                .enableSwipe(true)
                .load();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                getActivity().onBackPressed();
                break;
        }
        return true;
    }
}
