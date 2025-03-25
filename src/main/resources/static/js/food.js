// 지도 객체 및 인포윈도우 설정
var map;
var infowindow;
var ps;
var markers = [];
var apiKey = 'fsq3lAlEzX9ysp9gHcT2SVCqX87Pz55FdyLcyCThLf980lU='; // Foursquare API 키

// 지도 초기화
function initMap() {
    var mapContainer = document.getElementById('map'),
        mapOption = {
            center: new kakao.maps.LatLng(37.566826, 126.9786567), // 서울을 기본 중심으로 설정
            level: 3 // 확대 레벨 설정
        };
    
    // 지도 객체 생성
    map = new kakao.maps.Map(mapContainer, mapOption);
    
    // 장소 검색 객체 생성
    ps = new kakao.maps.services.Places();
    
    // 인포윈도우 객체 생성
    infowindow = new kakao.maps.InfoWindow({ zIndex: 1 });
}

// 검색 버튼 클릭 시 호출되는 함수
function searchPlace() {
    var query = document.getElementById('search-input').value;
    
    if (!query) {
        alert("검색어를 입력해주세요.");
        return;
    }

    // 키워드로 장소 검색
    ps.keywordSearch(query, placesSearchCB);
}

// 검색된 장소에 대해 마커를 표시하는 함수
function placesSearchCB(data, status, pagination) {
    if (status === kakao.maps.services.Status.OK) {
        var bounds = new kakao.maps.LatLngBounds(); // 검색된 장소 위치 범위 확장

        // 검색된 모든 장소에 대해 마커를 표시
        for (var i = 0; i < data.length; i++) {
            displayMarker(data[i]);
            bounds.extend(new kakao.maps.LatLng(data[i].y, data[i].x)); // 검색된 장소의 좌표를 bounds에 추가
        }

        // 지도 범위 재설정
        map.setBounds(bounds);

        // Foursquare API 호출하여 장소 사진 및 정보를 표시
        fetchFoursquareData(data);
    }
}

// 마커를 생성하여 지도에 표시하는 함수
function displayMarker(place) {
    var marker = new kakao.maps.Marker({
        map: map,
        position: new kakao.maps.LatLng(place.y, place.x) // 마커 위치 설정
    });

    // 마커 클릭 시 인포윈도우에 장소명 표시
    kakao.maps.event.addListener(marker, 'click', function() {
        infowindow.setContent('<div style="padding:5px; font-size:12px;">' + place.place_name + '</div>');
        infowindow.open(map, marker);
    });

    markers.push(marker); // 마커 목록에 추가
}

// Foursquare API 호출하여 장소 정보 및 사진을 표시하는 함수
function fetchFoursquareData(places) {
    var restaurantList = document.getElementById('restaurant-list');
    restaurantList.innerHTML = ""; // 이전에 표시된 내용 제거

    places.forEach(function(place, index) {
        var query = place.place_name;
        var url = `https://api.foursquare.com/v3/places/search?query=${query}&limit=1&near=Seoul&fields=fsq_id,name,location,photos`;

        // Foursquare API 호출
        fetch(url, {
            headers: {
                'Authorization': apiKey
            }
        })
        .then(response => response.json())
        .then(data => {
            var restaurant = data.results[0];
            if (restaurant) {
                var imageUrl = '/images/default-image.jpg'; // 기본 이미지

                // Foursquare에서 제공하는 사진이 있으면 이미지 URL 설정
                if (restaurant.photos && restaurant.photos[0]) {
                    const photo = restaurant.photos[0];
                    imageUrl = `${photo.prefix}500x500${photo.suffix}`; // 사진 크기 설정 (500x500)
                }

                // 맛집 리스트에 추가
                addRestaurantToList(restaurant, index, imageUrl);
            }
        })
        .catch(error => {
            console.error('Foursquare API 호출 실패:', error);
        });
    });
}

// 지도에서 해당 장소로 이동하는 함수
function showOnMap(index) {
    var position = markers[index].getPosition(); // markers 배열에서 해당 위치 가져오기
    map.panTo(position); // 지도에서 해당 위치로 이동
}

// 맛집 정보를 리스트에 추가하는 함수
function addRestaurantToList(restaurant, index, imageUrl) {
    var restaurantList = document.getElementById('restaurant-list');

    var div = document.createElement('div');
    div.classList.add('restaurant-item');
    div.innerHTML = `
        <div class="restaurant-image">
            <img src="${imageUrl}" alt="${restaurant.name}" />
        </div>
        <div class="info">
            <p><strong>${restaurant.name}</strong></p>
            <p>${restaurant.location.address || '주소 없음'}</p>
            <button onclick="showOnMap(${index})">지도에서 보기</button>
        </div>
    `;

    restaurantList.appendChild(div);
}


/**                         [리뷰와 별점 넣는 코드]
 * <div class="info">
                    <h3>${restaurant.name}</h3>
                    <p>${hotel.location.address || '주소 없음'}</p>
                    <div class="rating">
                        ⭐ ${rating} (${reviews.length}개의 리뷰)
                    </div>
                    <div class="reviews">
                        ${reviewsHtml}
                    </div>
                </div>
 * 
 * 
 */


// 페이지 로드시 지도 초기화
window.onload = initMap;
