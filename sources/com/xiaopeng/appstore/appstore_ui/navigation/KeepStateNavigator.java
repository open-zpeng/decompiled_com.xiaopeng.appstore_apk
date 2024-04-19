package com.xiaopeng.appstore.appstore_ui.navigation;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Lifecycle;
import androidx.navigation.NavDestination;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigator;
import androidx.navigation.fragment.FragmentNavigator;
import com.orhanobut.logger.Logger;
import com.xiaopeng.appstore.common_ui.BaseBizFragment;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;
@Navigator.Name("keep_state_fragment")
/* loaded from: classes2.dex */
public class KeepStateNavigator extends Navigator<FragmentNavigator.Destination> {
    private static final String KEY_BACK_STACK_IDS = "androidx-nav-fragment:navigator:backStackIds";
    private static final String TAG = "KeepStateNavigator";
    private final int mContainerId;
    private final Context mContext;
    private final FragmentManager mFragmentManager;
    private final ArrayDeque<Integer> mBackStack = new ArrayDeque<>();
    private final ArrayDeque<Integer> mEnterPopAnimStack = new ArrayDeque<>();
    private final ArrayDeque<Integer> mExitPopAnimStack = new ArrayDeque<>();

    public KeepStateNavigator(Context context, FragmentManager manager, int containerId) {
        this.mContext = context;
        this.mFragmentManager = manager;
        this.mContainerId = containerId;
    }

    public int getBackStackEntryCount() {
        return this.mBackStack.size();
    }

    @Override // androidx.navigation.Navigator
    public FragmentNavigator.Destination createDestination() {
        return new FragmentNavigator.Destination(this);
    }

    @Override // androidx.navigation.Navigator
    public NavDestination navigate(FragmentNavigator.Destination destination, Bundle args, NavOptions navOptions, Navigator.Extras navigatorExtras) {
        if (this.mFragmentManager.isStateSaved()) {
            Log.i(TAG, "Ignoring navigate() call: FragmentManager has already saved its state");
            return null;
        }
        String className = destination.getClassName();
        if (className.charAt(0) == '.') {
            className = this.mContext.getPackageName() + className;
        }
        Fragment findFragmentByTag = !this.mBackStack.isEmpty() ? this.mFragmentManager.findFragmentByTag(generateBackStackName(this.mBackStack.size() - 1, ((Integer) Optional.ofNullable(this.mBackStack.peekLast()).orElse(0)).intValue())) : null;
        if ((findFragmentByTag instanceof BaseBizFragment) && ((BaseBizFragment) findFragmentByTag).ignoreSameArguments(args)) {
            Logger.t(TAG).i("navigate, ignore same arguments, " + findFragmentByTag, new Object[0]);
            return null;
        }
        boolean isEmpty = this.mBackStack.isEmpty();
        int id = destination.getId();
        String generateBackStackName = generateBackStackName(this.mBackStack.size(), id);
        Fragment initFragment = initFragment(className, generateBackStackName);
        if (initFragment != findFragmentByTag) {
            FragmentTransaction beginTransaction = this.mFragmentManager.beginTransaction();
            int enterAnim = navOptions != null ? navOptions.getEnterAnim() : -1;
            int exitAnim = navOptions != null ? navOptions.getExitAnim() : -1;
            int popEnterAnim = navOptions != null ? navOptions.getPopEnterAnim() : -1;
            int popExitAnim = navOptions != null ? navOptions.getPopExitAnim() : -1;
            if (enterAnim != -1 || exitAnim != -1 || popEnterAnim != -1 || popExitAnim != -1) {
                if (enterAnim == -1) {
                    enterAnim = 0;
                }
                if (exitAnim == -1) {
                    exitAnim = 0;
                }
                if (popEnterAnim == -1) {
                    popEnterAnim = 0;
                }
                if (popExitAnim == -1) {
                    popExitAnim = 0;
                }
                beginTransaction.setCustomAnimations(enterAnim, exitAnim, popEnterAnim, popExitAnim);
            }
            if (findFragmentByTag != null) {
                boolean isInBackStack = isInBackStack(findFragmentByTag);
                Logger.t(TAG).i("isInBackStack=" + isInBackStack, new Object[0]);
                if (isInBackStack) {
                    beginTransaction.hide(findFragmentByTag);
                    beginTransaction.setMaxLifecycle(findFragmentByTag, Lifecycle.State.STARTED);
                }
            }
            beginTransaction.add(this.mContainerId, initFragment, generateBackStackName);
            Logger.t(TAG).i("navigate from:" + findFragmentByTag + ", to:" + initFragment, new Object[0]);
            initFragment.setArguments(args);
            beginTransaction.setPrimaryNavigationFragment(initFragment);
            Logger.t(TAG).i("before addStack: mBackStack=" + this.mBackStack + ", mExitPopAnimStack=" + this.mExitPopAnimStack + ", mEnterPopAnimStack=" + this.mEnterPopAnimStack, new Object[0]);
            this.mBackStack.add(Integer.valueOf(id));
            ArrayDeque<Integer> arrayDeque = this.mEnterPopAnimStack;
            if (popEnterAnim == -1) {
                popEnterAnim = 0;
            }
            arrayDeque.add(Integer.valueOf(popEnterAnim));
            ArrayDeque<Integer> arrayDeque2 = this.mExitPopAnimStack;
            if (popExitAnim == -1) {
                popExitAnim = 0;
            }
            arrayDeque2.add(Integer.valueOf(popExitAnim));
            Logger.t(TAG).i("after addStack: mBackStack=" + this.mBackStack + ", mExitPopAnimStack=" + this.mExitPopAnimStack + ", mEnterPopAnimStack=" + this.mEnterPopAnimStack, new Object[0]);
            if (navOptions != null && !isEmpty && navOptions.shouldLaunchSingleTop()) {
                this.mBackStack.peekLast().intValue();
            }
            if (navigatorExtras instanceof FragmentNavigator.Extras) {
                for (Map.Entry<View, String> entry : ((FragmentNavigator.Extras) navigatorExtras).getSharedElements().entrySet()) {
                    beginTransaction.addSharedElement(entry.getKey(), entry.getValue());
                }
            }
            beginTransaction.setReorderingAllowed(true);
            beginTransaction.commit();
            return destination;
        }
        return null;
    }

    private Fragment initFragment(String className, String tag) {
        return this.mFragmentManager.getFragmentFactory().instantiate(this.mContext.getClassLoader(), className);
    }

    @Override // androidx.navigation.Navigator
    public boolean popBackStack() {
        Logger.t(TAG).i("before popBackStack: mBackStack=" + this.mBackStack + ", mExitPopAnimStack=" + this.mExitPopAnimStack + ", mEnterPopAnimStack=" + this.mEnterPopAnimStack, new Object[0]);
        if (this.mBackStack.isEmpty()) {
            Logger.t(TAG).w("popBackStack return", new Object[0]);
            return false;
        } else if (this.mFragmentManager.isStateSaved()) {
            Logger.t(TAG).i("Ignoring popBackStack() call: FragmentManager has already saved its state", new Object[0]);
            return false;
        } else {
            Fragment findFragmentByTag = this.mFragmentManager.findFragmentByTag(generateBackStackName(this.mBackStack.size() - 1, ((Integer) Optional.ofNullable(this.mBackStack.peekLast()).orElse(0)).intValue()));
            this.mBackStack.removeLast();
            int intValue = ((Integer) Optional.ofNullable(this.mExitPopAnimStack.peekLast()).orElse(0)).intValue();
            if (!this.mExitPopAnimStack.isEmpty()) {
                this.mExitPopAnimStack.removeLast();
            } else {
                Logger.t(TAG).w("popBackStack, exitPopAnimState is empty", new Object[0]);
            }
            int intValue2 = ((Integer) Optional.ofNullable(this.mEnterPopAnimStack.peekLast()).orElse(0)).intValue();
            if (!this.mEnterPopAnimStack.isEmpty()) {
                this.mEnterPopAnimStack.removeLast();
            } else {
                Logger.t(TAG).w("popBackStack, enterPopAnimState is empty", new Object[0]);
            }
            FragmentTransaction beginTransaction = this.mFragmentManager.beginTransaction();
            beginTransaction.setCustomAnimations(intValue2, intValue);
            if (!this.mBackStack.isEmpty()) {
                Fragment findFragmentByTag2 = this.mFragmentManager.findFragmentByTag(generateBackStackName(this.mBackStack.size() - 1, this.mBackStack.peekLast().intValue()));
                if (findFragmentByTag2 != null) {
                    Logger.t(TAG).i("popup show:" + findFragmentByTag + ", pop:" + findFragmentByTag, new Object[0]);
                    beginTransaction.show(findFragmentByTag2);
                    beginTransaction.setPrimaryNavigationFragment(findFragmentByTag2);
                    beginTransaction.setMaxLifecycle(findFragmentByTag2, Lifecycle.State.RESUMED);
                }
            }
            if (findFragmentByTag != null) {
                beginTransaction.remove(findFragmentByTag);
            }
            beginTransaction.commit();
            Logger.t(TAG).i("after popBackStack: mBackStack=" + this.mBackStack + ", mExitPopAnimStack=" + this.mExitPopAnimStack + ", mEnterPopAnimStack=" + this.mEnterPopAnimStack, new Object[0]);
            return true;
        }
    }

    private boolean isInBackStack(Fragment fragment) {
        String tag = fragment.getTag();
        Iterator<Integer> it = this.mBackStack.iterator();
        int i = 0;
        while (it.hasNext()) {
            if (generateBackStackName(i, ((Integer) Optional.ofNullable(it.next()).orElse(-1)).intValue()).equals(tag)) {
                Logger.t(TAG).i("isInBackStack true, currentTag:" + tag + " " + fragment + ", list:" + this.mBackStack, new Object[0]);
                return true;
            }
            i++;
        }
        Logger.t(TAG).i("isInBackStack false, currentTag:" + tag + " " + fragment + ", list:" + this.mBackStack, new Object[0]);
        return false;
    }

    @Override // androidx.navigation.Navigator
    public Bundle onSaveState() {
        int i = 0;
        Logger.t(TAG).i("onSaveState " + this.mBackStack, new Object[0]);
        Bundle bundle = new Bundle();
        int[] iArr = new int[this.mBackStack.size()];
        Iterator<Integer> it = this.mBackStack.iterator();
        while (it.hasNext()) {
            iArr[i] = it.next().intValue();
            i++;
        }
        bundle.putIntArray(KEY_BACK_STACK_IDS, iArr);
        return bundle;
    }

    @Override // androidx.navigation.Navigator
    public void onRestoreState(Bundle savedState) {
        if (savedState != null) {
            int[] intArray = savedState.getIntArray(KEY_BACK_STACK_IDS);
            Logger.t(TAG).w("onRestoreState " + Arrays.toString(intArray), new Object[0]);
            if (intArray != null) {
                this.mBackStack.clear();
                for (int i : intArray) {
                    this.mBackStack.add(Integer.valueOf(i));
                }
            }
        }
    }

    private String generateBackStackName(int backStackIndex, int destId) {
        return backStackIndex + "-" + destId;
    }
}
