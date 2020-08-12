package com.assl.volleyapp;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.google.android.material.textfield.TextInputLayout;

import java.util.Objects;

public class SignUpFragment extends Fragment implements TextWatcher, DatePickerDialog.OnDateSetListener {
    private MainActivityViewModel mainActivityViewModel;
    private ConstraintLayout mainLayout;
    private TextInputLayout firstName, lastName, username, email, dob, phoneNumber, password;
    private Button signUpButton;
    private DatePickerDialog datePickerDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mainActivityViewModel = new ViewModelProvider(requireActivity()).get(MainActivityViewModel.class);
        return inflater.inflate(R.layout.fragment_sign_up, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        addFocusChangeListener();
        populateViews();
        addTextWatcher();
        addClickListener();
    }

    private void addClickListener() {
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                if(!Objects.requireNonNull(firstName.getEditText()).getText().toString().trim().equals("")
                        || !Objects.requireNonNull(lastName.getEditText()).getText().toString().trim().equals("")
                        || !Objects.requireNonNull(username.getEditText()).getText().toString().trim().equals("")
                        || !Objects.requireNonNull(email.getEditText()).getText().toString().trim().equals("")
                        || !Objects.requireNonNull(dob.getEditText()).getText().toString().trim().equals("")
                        || !Objects.requireNonNull(phoneNumber.getEditText()).getText().toString().trim().equals("")
                        || !Objects.requireNonNull(password.getEditText()).getText().toString().trim().equals("")){
                    Toast.makeText(requireContext(), "Creating user...", Toast.LENGTH_SHORT).show();
                    RegistrationBody registrationBody = new RegistrationBody();
                    registrationBody.setFirstName(firstName.getEditText().getText().toString().trim());
                    registrationBody.setLastName(Objects.requireNonNull(lastName.getEditText()).getText().toString().trim());
                    registrationBody.setUsername(Objects.requireNonNull(username.getEditText()).getText().toString().trim());
                    registrationBody.setEmailID(Objects.requireNonNull(email.getEditText()).getText().toString().trim());
                    registrationBody.setIsEmailVerified(1);
                    registrationBody.setMobileNumber(Objects.requireNonNull(phoneNumber.getEditText()).getText().toString().trim());
                    registrationBody.setIsMobileVerified(1);
                    registrationBody.setDateOfBirth(/*Objects.requireNonNull(dob.getEditText()).getText().toString().trim()*/"1982-07-12T00:00:00");
                    registrationBody.setPassword(Objects.requireNonNull(password.getEditText()).getText().toString().trim());
                    MutableLiveData<String> signUpResponse = mainActivityViewModel.createUser(registrationBody, requireContext());
                    signUpResponse.observe(getViewLifecycleOwner(), new Observer<String>() {
                        @Override
                        public void onChanged(String s) {
                            Log.e("Registration","response: "+s);
                            if(s.charAt(0)=='s') {
                                Toast.makeText(requireContext(), s.substring(2), Toast.LENGTH_SHORT).show();
                                mainActivityViewModel.setDob("");
                                mainActivityViewModel.setEmail("");
                                mainActivityViewModel.setFirstName("");
                                mainActivityViewModel.setLastName("");
                                mainActivityViewModel.setUsername("");
                                mainActivityViewModel.setPassword("");
                                mainActivityViewModel.setPhoneNumber("");
                                Navigation.findNavController(view).navigateUp();
                            }else
                                Toast.makeText(requireContext(), "Sign Up Failed", Toast.LENGTH_SHORT).show();
                        }
                    });
                }else
                    Toast.makeText(requireContext(), "All field's are required!!!", Toast.LENGTH_SHORT).show();
            }
        });

        Objects.requireNonNull(dob.getEditText()).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePickerDialog.show();
            }
        });
    }

    private void addTextWatcher() {
        Objects.requireNonNull(firstName.getEditText()).addTextChangedListener(this);
        Objects.requireNonNull(lastName.getEditText()).addTextChangedListener(this);
        Objects.requireNonNull(username.getEditText()).addTextChangedListener(this);
        Objects.requireNonNull(email.getEditText()).addTextChangedListener(this);
        Objects.requireNonNull(dob.getEditText()).addTextChangedListener(this);
        Objects.requireNonNull(phoneNumber.getEditText()).addTextChangedListener(this);
        Objects.requireNonNull(password.getEditText()).addTextChangedListener(this);
    }

    private void populateViews() {
        Objects.requireNonNull(firstName.getEditText()).setText(mainActivityViewModel.getFirstName().equals("")?"":mainActivityViewModel.getFirstName());
        Objects.requireNonNull(lastName.getEditText()).setText(mainActivityViewModel.getLastName().equals("")?"":mainActivityViewModel.getLastName());
        Objects.requireNonNull(username.getEditText()).setText(mainActivityViewModel.getUsername().equals("")?"":mainActivityViewModel.getUsername());
        Objects.requireNonNull(email.getEditText()).setText(mainActivityViewModel.getEmail().equals("")?"":mainActivityViewModel.getEmail());
        Objects.requireNonNull(dob.getEditText()).setText(mainActivityViewModel.getDob().equals("")?"":mainActivityViewModel.getDob());
        Objects.requireNonNull(phoneNumber.getEditText()).setText(mainActivityViewModel.getPhoneNumber().equals("")?"":mainActivityViewModel.getPhoneNumber());
        Objects.requireNonNull(password.getEditText()).setText(mainActivityViewModel.getPassword().equals("")?"":mainActivityViewModel.getPassword());
    }

    private void addFocusChangeListener() {
        mainLayout.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if(b && !(view instanceof EditText))
                    hideKeyboard(view);
            }
        });
    }

    private void hideKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) requireContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if(imm!=null)
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    private void initViews(View view) {
        mainLayout = view.findViewById(R.id.sign_up_main_layout);
        firstName = view.findViewById(R.id.first_name);
        lastName = view.findViewById(R.id.last_name);
        username = view.findViewById(R.id.user_name);
        email = view.findViewById(R.id.email);
        dob = view.findViewById(R.id.date_of_birth);
        phoneNumber = view.findViewById(R.id.mobile_number);
        password = view.findViewById(R.id.password);
        signUpButton = view.findViewById(R.id.sign_up_button);
        datePickerDialog = new DatePickerDialog(requireContext(), SignUpFragment.this, 2020, 1, 1);
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        String currentString = charSequence.toString();
        if (firstName.hasFocus())
            mainActivityViewModel.setFirstName(currentString);
        else if (lastName.hasFocus())
            mainActivityViewModel.setLastName(currentString);
        else if (username.hasFocus())
            mainActivityViewModel.setUsername(currentString);
        else if (email.hasFocus())
            mainActivityViewModel.setEmail(currentString);
        else if(dob.hasFocus())
            mainActivityViewModel.setDob(currentString);
        else if(phoneNumber.hasFocus())
            mainActivityViewModel.setPhoneNumber(currentString);
        else if(password.hasFocus())
            mainActivityViewModel.setPassword(currentString);
    }

    @Override
    public void afterTextChanged(Editable editable) {}

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
        String date = (day<10?"0"+day:day)    +"/"+   (month+1<10?"0"+(month+1):month+1)  +"/"+   year;
        Objects.requireNonNull(dob.getEditText()).setText(date);
    }
}