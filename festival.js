let map;
let service;

// 지도 초기화
function initMap() {
    map = new google.maps.Map(document.createElement('div'), {
        center: { lat: 37.7749, lng: -122.4194 },  // 기본 위치는 샌프란시스코
        zoom: 12
    });
    service = new google.maps.places.PlacesService(map);
}

// 지역에 맞는 축제 검색 함수
function searchFestivals() {
    const location = document.getElementById('search-bar').value;  // 수정된 부분
    const festivalListContainer = document.getElementById('festival-list');
    
    // 빈 입력 값이면 축제 목록 초기화
    if (!location) {
        festivalListContainer.innerHTML = '';
        return;
    }

    // 구글 지오코딩 API를 통해 지역을 좌표로 변환
    const geocoder = new google.maps.Geocoder();
    geocoder.geocode({ 'address': location }, function(results, status) {
        if (status === 'OK') {
            const lat = results[0].geometry.location.lat();
            const lng = results[0].geometry.location.lng();
            fetchFestivals(lat, lng);
        } else {
            festivalListContainer.innerHTML = '지역을 찾을 수 없습니다.';
        }
    });
}

// 축제 검색을 위한 Google Places API 호출
function fetchFestivals(lat, lng) {
    const request = {
        location: new google.maps.LatLng(lat, lng),
        radius: 5000,  // 검색 범위
        query: 'festival',  // 축제 검색
    };

    service.textSearch(request, function(results, status) {
        const festivalListContainer = document.getElementById('festival-list');
        if (status === google.maps.places.PlacesServiceStatus.OK) {
            displayFestivals(results);
        } else {
            festivalListContainer.innerHTML = '근처 축제를 찾을 수 없습니다.';
        }
    });
}

// 축제 정보 표시
function displayFestivals(festivals) {
    const festivalListContainer = document.getElementById('festival-list');
    festivalListContainer.innerHTML = '';  // 기존 목록 초기화

    if (festivals.length === 0) {
        festivalListContainer.innerHTML = '해당 지역에는 축제가 없습니다.';
        return;
    }

    festivals.forEach(festival => {
        // 이미지가 없는 경우, 해당 축제는 건너뜁니다.
        if (!festival.photos || festival.photos.length === 0) {
            return;  // 이미지가 없으면 축제 항목을 표시하지 않음
        }

        const festivalItem = document.createElement('div');
        festivalItem.classList.add('festival-item');

        // 축제 이미지
        const festivalImage = festival.photos && festival.photos.length > 0 ? festival.photos[0].getUrl() : 'no-image.jpg';
        
        festivalItem.innerHTML = `
            <div class="festival-text">
                <h3>${festival.name}</h3> <!-- 축제 이름 -->
                <img src="${festivalImage}" alt="${festival.name}" class="festival-image"> <!-- 축제 이미지 -->
                <p><strong>주소:</strong> ${festival.formatted_address || '주소 정보 없음'}</p> <!-- 축제 주소 -->
            </div>
        `;

        // 네이버 검색 링크 추가 (축제명 클릭 시 네이버에서 검색)
        festivalItem.onclick = function() {
            const festivalName = festival.name;
            const naverSearchUrl = `https://search.naver.com/search.naver?query=${encodeURIComponent(festivalName)}`;
            window.open(naverSearchUrl, '_blank');
        };

        // 애니메이션을 위해 class 'show' 추가
        setTimeout(() => festivalItem.classList.add('show'), 100);

        festivalListContainer.appendChild(festivalItem);
    });
}

// 페이지가 로드되면 지도 초기화
window.onload = initMap;
