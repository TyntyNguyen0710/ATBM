var imgMain=document.querySelector('.img-main')
var imgList=document.querySelectorAll('.list-img img')
var prevBtn=document.querySelector('.prev')
var nextBtn=document.querySelector('.next')
var currentIndex=0;

function updateimgbyIndex(index){
    document.querySelectorAll('.list-img div').forEach(item=>{
item.classList.remove('active')
    })
    currentIndex=index
    imgMain.src=imgList[index].getAttribute('src')
    imgList[index].parentElement.classList.add('active')
}
imgList.forEach((imgElement,index)=>{
imgElement.addEventListener('click',e=>{
    imgMain.style.opacity='0'
    setTimeout(()=>{
updateimgbyIndex(index)
imgMain.style.opacity='1'
    },400)

})
})
prevBtn.addEventListener('click',e=>{
   if(currentIndex==0){
    currentIndex=imgList.length-1
   }else{
    currentIndex--

   }
   imgMain.style.opacity='0'
    setTimeout(()=>{
updateimgbyIndex(currentIndex)
imgMain.style.opacity='1'
    },400)
})
nextBtn.addEventListener('click',e=>{
   if(currentIndex==imgList.length-1){
    currentIndex=0
   }else{
    currentIndex++
   }
   imgMain.style.opacity='0'
   setTimeout(()=>{
updateimgbyIndex(currentIndex)
imgMain.style.opacity='1'
   },400)
})
$(document).ready(function(){
    $('#eye').click(function(){
        $(this).toggleClass('open');
        $(this).children('i').toggleClass('fa-eye-slash fa-eye');
        if($(this).hasClass('open')){
            $(this).prev().attr('type', 'text');
        }else{
            $(this).prev().attr('type', 'password');
        }
    });
});
