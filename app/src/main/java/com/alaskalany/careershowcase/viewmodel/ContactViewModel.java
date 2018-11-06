package com.alaskalany.careershowcase.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import com.alaskalany.careershowcase.CareerShowcaseApp;
import com.alaskalany.careershowcase.entity.ContactEntity;
import com.alaskalany.careershowcase.repository.DataRepository;

public class ContactViewModel
        extends AndroidViewModel {

    private final LiveData<ContactEntity> observableContact;

    private final int contactId;

    public ObservableField<ContactEntity> contact = new ObservableField<>();

    public ContactViewModel(@NonNull Application application, DataRepository dataRepository, final int contactId) {

        super(application);
        this.contactId = contactId;
        observableContact = dataRepository.contactRepository.load(contactId);
    }

    public LiveData<ContactEntity> getObservableContact() {

        return observableContact;
    }

    public void setContact(ContactEntity contact) {

        this.contact.set(contact);
    }

    /**
     * A creator is used to inject the product ID into the ViewModel
     * <p>
     * This creator is to showcase how to inject dependencies into ViewModels. It's not
     * actually necessary in this case, as the product ID can be passed in a public method.
     */
    public static class Factory
            extends ViewModelProvider.NewInstanceFactory {

        @NonNull
        private final Application mApplication;

        private final int mContactId;

        private final DataRepository mRepository;

        public Factory(@NonNull Application application, int contactId) {

            mApplication = application;
            mContactId = contactId;
            mRepository = ((CareerShowcaseApp) application).getRepository();
        }

        @Override
        public <T extends ViewModel> T create(Class<T> modelClass) {
            //noinspection unchecked
            return (T) new ContactViewModel(mApplication, mRepository, mContactId);
        }
    }
}
