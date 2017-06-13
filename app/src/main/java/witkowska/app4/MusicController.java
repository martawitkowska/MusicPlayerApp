package witkowska.app4;

import android.content.Context;
import android.media.session.MediaSession;
import android.support.annotation.NonNull;
import android.widget.MediaController;

/**
 * Created by Administrator on 2017-06-13.
 */

public class MusicController extends MediaController {
    public MusicController(Context context) {
        super(context);
    }

    public void hide() {} //to stop it from automatically hiding after three seconds
}
