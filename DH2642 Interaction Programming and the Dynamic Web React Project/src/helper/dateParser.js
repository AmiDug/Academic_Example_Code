function javascriptToHTML(date){
    return date.toISOString().split("T")[0];
}

function todayXYearsAgo(years){
    const today=new Date();
    const year=today.getFullYear();
    const month=today.getMonth();
    const day=today.getDate();
    const date=new Date(year-years,month,day);
    return javascriptToHTML(date);
}

function birthdateToAge(date){
    const today=new Date();
    const birthDate=new Date(date);
    const age=Math.floor((today-birthDate)/(1000*60*60*24*365));
    return age;
}

export {javascriptToHTML,todayXYearsAgo,birthdateToAge};