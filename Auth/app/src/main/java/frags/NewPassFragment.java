package frags;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.project.auth.auth.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class NewPassFragment extends Fragment {

    public NewPassFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_new_pass, container, false);
    }
}
