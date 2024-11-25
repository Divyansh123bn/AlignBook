console.log("script");

// theme
let currentTheme=getTheme();
//intitally current theme
document.addEventListener('DOMContentLoaded',()=>{
    changeTheme();
})

//todo
function changeTheme(){
    //set to webpage
    document.querySelector('html').classList.add(currentTheme);
    //set the listener to change the theme button
    const changeThemeButton=document.querySelector("#theme_change_button");
    changeThemeButton.addEventListener('click',()=>{
        console.log("theme change called");
        const oldTheme=currentTheme;
        if(currentTheme=="light"){
            //to dark
            currentTheme="dark";
        }else{
            //to light
            currentTheme="light";
        }
        //update in local storage
        setTheme(currentTheme);
        //remove the current theme
        document.querySelector('html').classList.remove(oldTheme);
        //add the new theme
        document.querySelector('html').classList.add(currentTheme);
    });
}

//save theme to local storage
function setTheme(theme){
    localStorage.setItem("theme",theme);
}
//getting Theme
function getTheme(){
    let theme=localStorage.getItem("theme");
    return theme ? theme : "light";
}