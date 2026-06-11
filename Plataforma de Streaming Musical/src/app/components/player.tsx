import { Play, Pause, SkipBack, SkipForward, Heart, ChevronUp, X, Volume2, Shuffle, Repeat } from 'lucide-react';
import { useState } from 'react';

interface PlayerProps {
  song?: { title: string; artist: string; gradient: string };
}

export function Player({ song }: PlayerProps) {
  const [isLiked, setIsLiked] = useState(false);
  const [isPlaying, setIsPlaying] = useState(false);
  const [expanded, setExpanded] = useState(false);
  const [progress, setProgress] = useState(33);

  const currentSong = song ?? {
    title: 'Midnight Dreams',
    artist: 'Luna Wave',
    gradient: 'from-yellow-400 to-amber-600',
  };

  if (expanded) {
    return (
      <div
        className="fixed inset-0 z-50 flex flex-col"
        style={{ background: '#0f0f0f' }}
      >
        {/* Top bar */}
        <div className="flex items-center justify-between px-4 pt-10 pb-4">
          <button onClick={() => setExpanded(false)}>
            <ChevronUp size={24} style={{ color: '#888888' }} />
          </button>
          <span style={{ color: '#888888', fontSize: '13px', fontWeight: 600 }}>REPRODUCIENDO AHORA</span>
          <button>
            <X size={24} style={{ color: 'transparent' }} />
          </button>
        </div>

        {/* Album art */}
        <div className="px-8 flex-1 flex flex-col justify-center">
          <div
            className={`w-full aspect-square rounded-3xl bg-gradient-to-br ${currentSong.gradient} mb-8 flex items-center justify-center`}
            style={{ boxShadow: '0 24px 60px rgba(245,196,0,0.25)' }}
          >
            <div style={{ fontSize: '80px' }}>🎵</div>
          </div>

          {/* Song info */}
          <div className="flex items-center justify-between mb-6">
            <div>
              <p style={{ color: '#f5f5f5', fontSize: '22px', fontWeight: 700 }}>{currentSong.title}</p>
              <p style={{ color: '#888888', fontSize: '15px' }}>{currentSong.artist}</p>
            </div>
            <button onClick={() => setIsLiked(!isLiked)}>
              <Heart
                size={26}
                style={{
                  color: isLiked ? '#f5c400' : '#555555',
                  fill: isLiked ? '#f5c400' : 'none',
                }}
              />
            </button>
          </div>

          {/* Progress bar */}
          <div className="mb-2">
            <div
              className="h-1.5 rounded-full overflow-hidden cursor-pointer"
              style={{ background: '#2a2a2a' }}
              onClick={(e) => {
                const rect = e.currentTarget.getBoundingClientRect();
                setProgress(Math.round(((e.clientX - rect.left) / rect.width) * 100));
              }}
            >
              <div
                className="h-full rounded-full"
                style={{ background: '#f5c400', width: `${progress}%` }}
              />
            </div>
            <div className="flex justify-between mt-1.5">
              <span style={{ color: '#555555', fontSize: '12px' }}>1:15</span>
              <span style={{ color: '#555555', fontSize: '12px' }}>3:45</span>
            </div>
          </div>

          {/* Controls */}
          <div className="flex items-center justify-between mt-2 mb-6">
            <button>
              <Shuffle size={22} style={{ color: '#555555' }} />
            </button>
            <button>
              <SkipBack size={28} style={{ color: '#f5f5f5' }} />
            </button>
            <button
              onClick={() => setIsPlaying(!isPlaying)}
              className="w-16 h-16 rounded-full flex items-center justify-center"
              style={{ background: '#f5c400' }}
            >
              {isPlaying
                ? <Pause size={28} style={{ color: '#0f0f0f' }} fill="#0f0f0f" />
                : <Play size={28} style={{ color: '#0f0f0f', marginLeft: '3px' }} fill="#0f0f0f" />
              }
            </button>
            <button>
              <SkipForward size={28} style={{ color: '#f5f5f5' }} />
            </button>
            <button>
              <Repeat size={22} style={{ color: '#555555' }} />
            </button>
          </div>

          {/* Volume */}
          <div className="flex items-center gap-3 mb-8">
            <Volume2 size={18} style={{ color: '#555555' }} />
            <div
              className="flex-1 h-1 rounded-full"
              style={{ background: '#2a2a2a' }}
            >
              <div className="h-full rounded-full w-2/3" style={{ background: '#888888' }} />
            </div>
          </div>
        </div>
      </div>
    );
  }

  /* Mini player */
  return (
    <div
      className="fixed bottom-0 left-0 right-0 z-40 px-4 pb-5 pt-2"
      style={{ background: 'linear-gradient(to top, #0f0f0f 70%, transparent)' }}
    >
      <div
        className="rounded-2xl overflow-hidden cursor-pointer"
        style={{ background: '#1a1a1a', border: '1px solid rgba(255,255,255,0.08)' }}
        onClick={() => setExpanded(true)}
      >
        {/* Progress indicator */}
        <div className="h-0.5 w-full" style={{ background: '#2a2a2a' }}>
          <div className="h-full" style={{ background: '#f5c400', width: `${progress}%` }} />
        </div>

        <div className="flex items-center gap-3 px-4 py-3">
          <div
            className={`w-11 h-11 rounded-xl flex-shrink-0 bg-gradient-to-br ${currentSong.gradient}`}
          />

          <div className="flex-1 min-w-0">
            <p style={{ color: '#f5f5f5', fontSize: '14px', fontWeight: 600 }} className="truncate">
              {currentSong.title}
            </p>
            <p style={{ color: '#888888', fontSize: '12px' }} className="truncate">
              {currentSong.artist}
            </p>
          </div>

          <button
            onClick={(e) => { e.stopPropagation(); setIsLiked(!isLiked); }}
            className="flex-shrink-0 w-9 h-9 flex items-center justify-center"
          >
            <Heart
              size={20}
              style={{
                color: isLiked ? '#f5c400' : '#555555',
                fill: isLiked ? '#f5c400' : 'none',
              }}
            />
          </button>

          <button
            onClick={(e) => { e.stopPropagation(); setIsPlaying(!isPlaying); }}
            className="flex-shrink-0 w-11 h-11 rounded-full flex items-center justify-center"
            style={{ background: '#f5c400' }}
          >
            {isPlaying
              ? <Pause size={18} style={{ color: '#0f0f0f' }} fill="#0f0f0f" />
              : <Play size={18} style={{ color: '#0f0f0f', marginLeft: '2px' }} fill="#0f0f0f" />
            }
          </button>

          <button
            onClick={(e) => { e.stopPropagation(); }}
            className="flex-shrink-0"
          >
            <SkipForward size={20} style={{ color: '#888888' }} />
          </button>
        </div>
      </div>
    </div>
  );
}
