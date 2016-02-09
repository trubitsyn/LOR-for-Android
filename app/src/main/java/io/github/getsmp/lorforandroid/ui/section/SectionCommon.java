package io.github.getsmp.lorforandroid.ui.section;

import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;

import java.io.UnsupportedEncodingException;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import io.github.getsmp.lorforandroid.R;
import io.github.getsmp.lorforandroid.ui.base.BaseCallbackFragment;

public abstract class SectionCommon extends BaseCallbackFragment {
    private final AsyncHttpClient client = new AsyncHttpClient();
    protected int offset;

    @Override
    protected void getListItems() {
        if (offset <= getMaxOffset()) {
            client.get("https://www.linux.org.ru/" + getPath() + "/", getRequestParams(), new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    String resp = null;
                    try {
                        resp = new String(responseBody, "UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        // Will never execute
                    }

                    try {
                        generateDataSet(Jsoup.parse(resp).body());
                    } catch (NullPointerException e) {
                        showErrorView(R.string.error_npe);
                    }

                    offset += getItemsPerPage();
                    adapter.notifyDataSetChanged();
                    stopRefresh();
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                    showErrorView(R.string.error_network);
                }
            });
        } else Toast.makeText(context, R.string.error_no_more_data, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void clearData() {
        getDataSet().clear();
        offset = 0;
    }

    protected abstract List getDataSet();

    public abstract int getItemsPerPage();

    public abstract int getMaxOffset();

    public abstract String getPath();

    public abstract RequestParams getRequestParams();

    protected abstract void generateDataSet(Element responseBody);
}
