package com.notus.fit.network.misfit;

import com.notus.fit.models.misfit.MisfitSummary;

import retrofit.Callback;

// Referenced classes of package com.notus.fit.network.misfit:
//            RequestTokenBody

public interface MisfitApiInterface {

    public abstract void getAccessToken(RequestTokenBody requesttokenbody, Callback callback);

    public abstract MisfitSummary getSummary(String s, String s1, boolean flag);
}
